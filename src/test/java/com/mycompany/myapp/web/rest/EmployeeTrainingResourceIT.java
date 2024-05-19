package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.EmployeeTrainingAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.EmployeeTraining;
import com.mycompany.myapp.domain.TrainingProgram;
import com.mycompany.myapp.repository.EmployeeTrainingRepository;
import com.mycompany.myapp.service.dto.EmployeeTrainingDTO;
import com.mycompany.myapp.service.mapper.EmployeeTrainingMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EmployeeTrainingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeTrainingResourceIT {

    private static final String DEFAULT_COMPLETION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_COMPLETION_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COMPLETION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMPLETION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_COMPLETION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/employee-trainings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmployeeTrainingRepository employeeTrainingRepository;

    @Autowired
    private EmployeeTrainingMapper employeeTrainingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeTrainingMockMvc;

    private EmployeeTraining employeeTraining;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeTraining createEntity(EntityManager em) {
        EmployeeTraining employeeTraining = new EmployeeTraining()
            .completionStatus(DEFAULT_COMPLETION_STATUS)
            .completionDate(DEFAULT_COMPLETION_DATE);
        return employeeTraining;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeTraining createUpdatedEntity(EntityManager em) {
        EmployeeTraining employeeTraining = new EmployeeTraining()
            .completionStatus(UPDATED_COMPLETION_STATUS)
            .completionDate(UPDATED_COMPLETION_DATE);
        return employeeTraining;
    }

    @BeforeEach
    public void initTest() {
        employeeTraining = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeTraining() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EmployeeTraining
        EmployeeTrainingDTO employeeTrainingDTO = employeeTrainingMapper.toDto(employeeTraining);
        var returnedEmployeeTrainingDTO = om.readValue(
            restEmployeeTrainingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employeeTrainingDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EmployeeTrainingDTO.class
        );

        // Validate the EmployeeTraining in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEmployeeTraining = employeeTrainingMapper.toEntity(returnedEmployeeTrainingDTO);
        assertEmployeeTrainingUpdatableFieldsEquals(returnedEmployeeTraining, getPersistedEmployeeTraining(returnedEmployeeTraining));
    }

    @Test
    @Transactional
    void createEmployeeTrainingWithExistingId() throws Exception {
        // Create the EmployeeTraining with an existing ID
        employeeTraining.setId(1L);
        EmployeeTrainingDTO employeeTrainingDTO = employeeTrainingMapper.toDto(employeeTraining);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeTrainingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employeeTrainingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeTraining in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeTrainings() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get all the employeeTrainingList
        restEmployeeTrainingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeTraining.getId().intValue())))
            .andExpect(jsonPath("$.[*].completionStatus").value(hasItem(DEFAULT_COMPLETION_STATUS)))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(DEFAULT_COMPLETION_DATE.toString())));
    }

    @Test
    @Transactional
    void getEmployeeTraining() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get the employeeTraining
        restEmployeeTrainingMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeTraining.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeTraining.getId().intValue()))
            .andExpect(jsonPath("$.completionStatus").value(DEFAULT_COMPLETION_STATUS))
            .andExpect(jsonPath("$.completionDate").value(DEFAULT_COMPLETION_DATE.toString()));
    }

    @Test
    @Transactional
    void getEmployeeTrainingsByIdFiltering() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        Long id = employeeTraining.getId();

        defaultEmployeeTrainingFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultEmployeeTrainingFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultEmployeeTrainingFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByCompletionStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get all the employeeTrainingList where completionStatus equals to
        defaultEmployeeTrainingFiltering(
            "completionStatus.equals=" + DEFAULT_COMPLETION_STATUS,
            "completionStatus.equals=" + UPDATED_COMPLETION_STATUS
        );
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByCompletionStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get all the employeeTrainingList where completionStatus in
        defaultEmployeeTrainingFiltering(
            "completionStatus.in=" + DEFAULT_COMPLETION_STATUS + "," + UPDATED_COMPLETION_STATUS,
            "completionStatus.in=" + UPDATED_COMPLETION_STATUS
        );
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByCompletionStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get all the employeeTrainingList where completionStatus is not null
        defaultEmployeeTrainingFiltering("completionStatus.specified=true", "completionStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByCompletionStatusContainsSomething() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get all the employeeTrainingList where completionStatus contains
        defaultEmployeeTrainingFiltering(
            "completionStatus.contains=" + DEFAULT_COMPLETION_STATUS,
            "completionStatus.contains=" + UPDATED_COMPLETION_STATUS
        );
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByCompletionStatusNotContainsSomething() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get all the employeeTrainingList where completionStatus does not contain
        defaultEmployeeTrainingFiltering(
            "completionStatus.doesNotContain=" + UPDATED_COMPLETION_STATUS,
            "completionStatus.doesNotContain=" + DEFAULT_COMPLETION_STATUS
        );
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByCompletionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get all the employeeTrainingList where completionDate equals to
        defaultEmployeeTrainingFiltering(
            "completionDate.equals=" + DEFAULT_COMPLETION_DATE,
            "completionDate.equals=" + UPDATED_COMPLETION_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByCompletionDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get all the employeeTrainingList where completionDate in
        defaultEmployeeTrainingFiltering(
            "completionDate.in=" + DEFAULT_COMPLETION_DATE + "," + UPDATED_COMPLETION_DATE,
            "completionDate.in=" + UPDATED_COMPLETION_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByCompletionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get all the employeeTrainingList where completionDate is not null
        defaultEmployeeTrainingFiltering("completionDate.specified=true", "completionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByCompletionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get all the employeeTrainingList where completionDate is greater than or equal to
        defaultEmployeeTrainingFiltering(
            "completionDate.greaterThanOrEqual=" + DEFAULT_COMPLETION_DATE,
            "completionDate.greaterThanOrEqual=" + UPDATED_COMPLETION_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByCompletionDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get all the employeeTrainingList where completionDate is less than or equal to
        defaultEmployeeTrainingFiltering(
            "completionDate.lessThanOrEqual=" + DEFAULT_COMPLETION_DATE,
            "completionDate.lessThanOrEqual=" + SMALLER_COMPLETION_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByCompletionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get all the employeeTrainingList where completionDate is less than
        defaultEmployeeTrainingFiltering(
            "completionDate.lessThan=" + UPDATED_COMPLETION_DATE,
            "completionDate.lessThan=" + DEFAULT_COMPLETION_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByCompletionDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        // Get all the employeeTrainingList where completionDate is greater than
        defaultEmployeeTrainingFiltering(
            "completionDate.greaterThan=" + SMALLER_COMPLETION_DATE,
            "completionDate.greaterThan=" + DEFAULT_COMPLETION_DATE
        );
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByTrainingProgramIsEqualToSomething() throws Exception {
        TrainingProgram trainingProgram;
        if (TestUtil.findAll(em, TrainingProgram.class).isEmpty()) {
            employeeTrainingRepository.saveAndFlush(employeeTraining);
            trainingProgram = TrainingProgramResourceIT.createEntity(em);
        } else {
            trainingProgram = TestUtil.findAll(em, TrainingProgram.class).get(0);
        }
        em.persist(trainingProgram);
        em.flush();
        employeeTraining.setTrainingProgram(trainingProgram);
        employeeTrainingRepository.saveAndFlush(employeeTraining);
        Long trainingProgramId = trainingProgram.getId();
        // Get all the employeeTrainingList where trainingProgram equals to trainingProgramId
        defaultEmployeeTrainingShouldBeFound("trainingProgramId.equals=" + trainingProgramId);

        // Get all the employeeTrainingList where trainingProgram equals to (trainingProgramId + 1)
        defaultEmployeeTrainingShouldNotBeFound("trainingProgramId.equals=" + (trainingProgramId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeTrainingsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employeeTrainingRepository.saveAndFlush(employeeTraining);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeTraining.setEmployee(employee);
        employeeTrainingRepository.saveAndFlush(employeeTraining);
        Long employeeId = employee.getId();
        // Get all the employeeTrainingList where employee equals to employeeId
        defaultEmployeeTrainingShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeTrainingList where employee equals to (employeeId + 1)
        defaultEmployeeTrainingShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    private void defaultEmployeeTrainingFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEmployeeTrainingShouldBeFound(shouldBeFound);
        defaultEmployeeTrainingShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeTrainingShouldBeFound(String filter) throws Exception {
        restEmployeeTrainingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeTraining.getId().intValue())))
            .andExpect(jsonPath("$.[*].completionStatus").value(hasItem(DEFAULT_COMPLETION_STATUS)))
            .andExpect(jsonPath("$.[*].completionDate").value(hasItem(DEFAULT_COMPLETION_DATE.toString())));

        // Check, that the count call also returns 1
        restEmployeeTrainingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeTrainingShouldNotBeFound(String filter) throws Exception {
        restEmployeeTrainingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeTrainingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeTraining() throws Exception {
        // Get the employeeTraining
        restEmployeeTrainingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeTraining() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the employeeTraining
        EmployeeTraining updatedEmployeeTraining = employeeTrainingRepository.findById(employeeTraining.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmployeeTraining are not directly saved in db
        em.detach(updatedEmployeeTraining);
        updatedEmployeeTraining.completionStatus(UPDATED_COMPLETION_STATUS).completionDate(UPDATED_COMPLETION_DATE);
        EmployeeTrainingDTO employeeTrainingDTO = employeeTrainingMapper.toDto(updatedEmployeeTraining);

        restEmployeeTrainingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeTrainingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(employeeTrainingDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeTraining in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmployeeTrainingToMatchAllProperties(updatedEmployeeTraining);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeTraining() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employeeTraining.setId(longCount.incrementAndGet());

        // Create the EmployeeTraining
        EmployeeTrainingDTO employeeTrainingDTO = employeeTrainingMapper.toDto(employeeTraining);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeTrainingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeTrainingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(employeeTrainingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeTraining in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeTraining() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employeeTraining.setId(longCount.incrementAndGet());

        // Create the EmployeeTraining
        EmployeeTrainingDTO employeeTrainingDTO = employeeTrainingMapper.toDto(employeeTraining);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeTrainingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(employeeTrainingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeTraining in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeTraining() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employeeTraining.setId(longCount.incrementAndGet());

        // Create the EmployeeTraining
        EmployeeTrainingDTO employeeTrainingDTO = employeeTrainingMapper.toDto(employeeTraining);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeTrainingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(employeeTrainingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeTraining in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeTrainingWithPatch() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the employeeTraining using partial update
        EmployeeTraining partialUpdatedEmployeeTraining = new EmployeeTraining();
        partialUpdatedEmployeeTraining.setId(employeeTraining.getId());

        partialUpdatedEmployeeTraining.completionDate(UPDATED_COMPLETION_DATE);

        restEmployeeTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeTraining.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmployeeTraining))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeTraining in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmployeeTrainingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEmployeeTraining, employeeTraining),
            getPersistedEmployeeTraining(employeeTraining)
        );
    }

    @Test
    @Transactional
    void fullUpdateEmployeeTrainingWithPatch() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the employeeTraining using partial update
        EmployeeTraining partialUpdatedEmployeeTraining = new EmployeeTraining();
        partialUpdatedEmployeeTraining.setId(employeeTraining.getId());

        partialUpdatedEmployeeTraining.completionStatus(UPDATED_COMPLETION_STATUS).completionDate(UPDATED_COMPLETION_DATE);

        restEmployeeTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeTraining.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmployeeTraining))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeTraining in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmployeeTrainingUpdatableFieldsEquals(
            partialUpdatedEmployeeTraining,
            getPersistedEmployeeTraining(partialUpdatedEmployeeTraining)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeTraining() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employeeTraining.setId(longCount.incrementAndGet());

        // Create the EmployeeTraining
        EmployeeTrainingDTO employeeTrainingDTO = employeeTrainingMapper.toDto(employeeTraining);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeTrainingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(employeeTrainingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeTraining in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeTraining() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employeeTraining.setId(longCount.incrementAndGet());

        // Create the EmployeeTraining
        EmployeeTrainingDTO employeeTrainingDTO = employeeTrainingMapper.toDto(employeeTraining);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeTrainingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(employeeTrainingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeTraining in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeTraining() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        employeeTraining.setId(longCount.incrementAndGet());

        // Create the EmployeeTraining
        EmployeeTrainingDTO employeeTrainingDTO = employeeTrainingMapper.toDto(employeeTraining);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeTrainingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(employeeTrainingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeTraining in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeTraining() throws Exception {
        // Initialize the database
        employeeTrainingRepository.saveAndFlush(employeeTraining);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the employeeTraining
        restEmployeeTrainingMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeTraining.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return employeeTrainingRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected EmployeeTraining getPersistedEmployeeTraining(EmployeeTraining employeeTraining) {
        return employeeTrainingRepository.findById(employeeTraining.getId()).orElseThrow();
    }

    protected void assertPersistedEmployeeTrainingToMatchAllProperties(EmployeeTraining expectedEmployeeTraining) {
        assertEmployeeTrainingAllPropertiesEquals(expectedEmployeeTraining, getPersistedEmployeeTraining(expectedEmployeeTraining));
    }

    protected void assertPersistedEmployeeTrainingToMatchUpdatableProperties(EmployeeTraining expectedEmployeeTraining) {
        assertEmployeeTrainingAllUpdatablePropertiesEquals(
            expectedEmployeeTraining,
            getPersistedEmployeeTraining(expectedEmployeeTraining)
        );
    }
}
