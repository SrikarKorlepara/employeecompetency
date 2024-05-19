package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CompetencyAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Competency;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.repository.CompetencyRepository;
import com.mycompany.myapp.service.dto.CompetencyDTO;
import com.mycompany.myapp.service.mapper.CompetencyMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link CompetencyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompetencyResourceIT {

    private static final Integer DEFAULT_COMPETENCY_ID = 1;
    private static final Integer UPDATED_COMPETENCY_ID = 2;
    private static final Integer SMALLER_COMPETENCY_ID = 1 - 1;

    private static final String DEFAULT_COMPETENCY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPETENCY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/competencies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CompetencyRepository competencyRepository;

    @Autowired
    private CompetencyMapper competencyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetencyMockMvc;

    private Competency competency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competency createEntity(EntityManager em) {
        Competency competency = new Competency()
            .competencyId(DEFAULT_COMPETENCY_ID)
            .competencyName(DEFAULT_COMPETENCY_NAME)
            .description(DEFAULT_DESCRIPTION);
        return competency;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competency createUpdatedEntity(EntityManager em) {
        Competency competency = new Competency()
            .competencyId(UPDATED_COMPETENCY_ID)
            .competencyName(UPDATED_COMPETENCY_NAME)
            .description(UPDATED_DESCRIPTION);
        return competency;
    }

    @BeforeEach
    public void initTest() {
        competency = createEntity(em);
    }

    @Test
    @Transactional
    void createCompetency() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);
        var returnedCompetencyDTO = om.readValue(
            restCompetencyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competencyDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CompetencyDTO.class
        );

        // Validate the Competency in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCompetency = competencyMapper.toEntity(returnedCompetencyDTO);
        assertCompetencyUpdatableFieldsEquals(returnedCompetency, getPersistedCompetency(returnedCompetency));
    }

    @Test
    @Transactional
    void createCompetencyWithExistingId() throws Exception {
        // Create the Competency with an existing ID
        competency.setId(1L);
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competency in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompetencies() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList
        restCompetencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competency.getId().intValue())))
            .andExpect(jsonPath("$.[*].competencyId").value(hasItem(DEFAULT_COMPETENCY_ID)))
            .andExpect(jsonPath("$.[*].competencyName").value(hasItem(DEFAULT_COMPETENCY_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    void getCompetency() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get the competency
        restCompetencyMockMvc
            .perform(get(ENTITY_API_URL_ID, competency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competency.getId().intValue()))
            .andExpect(jsonPath("$.competencyId").value(DEFAULT_COMPETENCY_ID))
            .andExpect(jsonPath("$.competencyName").value(DEFAULT_COMPETENCY_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    void getCompetenciesByIdFiltering() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        Long id = competency.getId();

        defaultCompetencyFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCompetencyFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCompetencyFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCompetencyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where competencyId equals to
        defaultCompetencyFiltering("competencyId.equals=" + DEFAULT_COMPETENCY_ID, "competencyId.equals=" + UPDATED_COMPETENCY_ID);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCompetencyIdIsInShouldWork() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where competencyId in
        defaultCompetencyFiltering(
            "competencyId.in=" + DEFAULT_COMPETENCY_ID + "," + UPDATED_COMPETENCY_ID,
            "competencyId.in=" + UPDATED_COMPETENCY_ID
        );
    }

    @Test
    @Transactional
    void getAllCompetenciesByCompetencyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where competencyId is not null
        defaultCompetencyFiltering("competencyId.specified=true", "competencyId.specified=false");
    }

    @Test
    @Transactional
    void getAllCompetenciesByCompetencyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where competencyId is greater than or equal to
        defaultCompetencyFiltering(
            "competencyId.greaterThanOrEqual=" + DEFAULT_COMPETENCY_ID,
            "competencyId.greaterThanOrEqual=" + UPDATED_COMPETENCY_ID
        );
    }

    @Test
    @Transactional
    void getAllCompetenciesByCompetencyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where competencyId is less than or equal to
        defaultCompetencyFiltering(
            "competencyId.lessThanOrEqual=" + DEFAULT_COMPETENCY_ID,
            "competencyId.lessThanOrEqual=" + SMALLER_COMPETENCY_ID
        );
    }

    @Test
    @Transactional
    void getAllCompetenciesByCompetencyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where competencyId is less than
        defaultCompetencyFiltering("competencyId.lessThan=" + UPDATED_COMPETENCY_ID, "competencyId.lessThan=" + DEFAULT_COMPETENCY_ID);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCompetencyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where competencyId is greater than
        defaultCompetencyFiltering(
            "competencyId.greaterThan=" + SMALLER_COMPETENCY_ID,
            "competencyId.greaterThan=" + DEFAULT_COMPETENCY_ID
        );
    }

    @Test
    @Transactional
    void getAllCompetenciesByCompetencyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where competencyName equals to
        defaultCompetencyFiltering("competencyName.equals=" + DEFAULT_COMPETENCY_NAME, "competencyName.equals=" + UPDATED_COMPETENCY_NAME);
    }

    @Test
    @Transactional
    void getAllCompetenciesByCompetencyNameIsInShouldWork() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where competencyName in
        defaultCompetencyFiltering(
            "competencyName.in=" + DEFAULT_COMPETENCY_NAME + "," + UPDATED_COMPETENCY_NAME,
            "competencyName.in=" + UPDATED_COMPETENCY_NAME
        );
    }

    @Test
    @Transactional
    void getAllCompetenciesByCompetencyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where competencyName is not null
        defaultCompetencyFiltering("competencyName.specified=true", "competencyName.specified=false");
    }

    @Test
    @Transactional
    void getAllCompetenciesByCompetencyNameContainsSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where competencyName contains
        defaultCompetencyFiltering(
            "competencyName.contains=" + DEFAULT_COMPETENCY_NAME,
            "competencyName.contains=" + UPDATED_COMPETENCY_NAME
        );
    }

    @Test
    @Transactional
    void getAllCompetenciesByCompetencyNameNotContainsSomething() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        // Get all the competencyList where competencyName does not contain
        defaultCompetencyFiltering(
            "competencyName.doesNotContain=" + UPDATED_COMPETENCY_NAME,
            "competencyName.doesNotContain=" + DEFAULT_COMPETENCY_NAME
        );
    }

    @Test
    @Transactional
    void getAllCompetenciesByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            competencyRepository.saveAndFlush(competency);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        competency.addEmployee(employee);
        competencyRepository.saveAndFlush(competency);
        Long employeeId = employee.getId();
        // Get all the competencyList where employee equals to employeeId
        defaultCompetencyShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the competencyList where employee equals to (employeeId + 1)
        defaultCompetencyShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    private void defaultCompetencyFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCompetencyShouldBeFound(shouldBeFound);
        defaultCompetencyShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompetencyShouldBeFound(String filter) throws Exception {
        restCompetencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competency.getId().intValue())))
            .andExpect(jsonPath("$.[*].competencyId").value(hasItem(DEFAULT_COMPETENCY_ID)))
            .andExpect(jsonPath("$.[*].competencyName").value(hasItem(DEFAULT_COMPETENCY_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));

        // Check, that the count call also returns 1
        restCompetencyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompetencyShouldNotBeFound(String filter) throws Exception {
        restCompetencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompetencyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompetency() throws Exception {
        // Get the competency
        restCompetencyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompetency() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the competency
        Competency updatedCompetency = competencyRepository.findById(competency.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCompetency are not directly saved in db
        em.detach(updatedCompetency);
        updatedCompetency.competencyId(UPDATED_COMPETENCY_ID).competencyName(UPDATED_COMPETENCY_NAME).description(UPDATED_DESCRIPTION);
        CompetencyDTO competencyDTO = competencyMapper.toDto(updatedCompetency);

        restCompetencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competencyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(competencyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Competency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCompetencyToMatchAllProperties(updatedCompetency);
    }

    @Test
    @Transactional
    void putNonExistingCompetency() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competency.setId(longCount.incrementAndGet());

        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competencyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(competencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompetency() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competency.setId(longCount.incrementAndGet());

        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(competencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompetency() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competency.setId(longCount.incrementAndGet());

        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetencyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competencyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompetencyWithPatch() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the competency using partial update
        Competency partialUpdatedCompetency = new Competency();
        partialUpdatedCompetency.setId(competency.getId());

        restCompetencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetency.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCompetency))
            )
            .andExpect(status().isOk());

        // Validate the Competency in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCompetencyUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCompetency, competency),
            getPersistedCompetency(competency)
        );
    }

    @Test
    @Transactional
    void fullUpdateCompetencyWithPatch() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the competency using partial update
        Competency partialUpdatedCompetency = new Competency();
        partialUpdatedCompetency.setId(competency.getId());

        partialUpdatedCompetency
            .competencyId(UPDATED_COMPETENCY_ID)
            .competencyName(UPDATED_COMPETENCY_NAME)
            .description(UPDATED_DESCRIPTION);

        restCompetencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetency.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCompetency))
            )
            .andExpect(status().isOk());

        // Validate the Competency in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCompetencyUpdatableFieldsEquals(partialUpdatedCompetency, getPersistedCompetency(partialUpdatedCompetency));
    }

    @Test
    @Transactional
    void patchNonExistingCompetency() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competency.setId(longCount.incrementAndGet());

        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competencyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(competencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompetency() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competency.setId(longCount.incrementAndGet());

        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(competencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompetency() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competency.setId(longCount.incrementAndGet());

        // Create the Competency
        CompetencyDTO competencyDTO = competencyMapper.toDto(competency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetencyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(competencyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competency in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompetency() throws Exception {
        // Initialize the database
        competencyRepository.saveAndFlush(competency);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the competency
        restCompetencyMockMvc
            .perform(delete(ENTITY_API_URL_ID, competency.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return competencyRepository.count();
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

    protected Competency getPersistedCompetency(Competency competency) {
        return competencyRepository.findById(competency.getId()).orElseThrow();
    }

    protected void assertPersistedCompetencyToMatchAllProperties(Competency expectedCompetency) {
        assertCompetencyAllPropertiesEquals(expectedCompetency, getPersistedCompetency(expectedCompetency));
    }

    protected void assertPersistedCompetencyToMatchUpdatableProperties(Competency expectedCompetency) {
        assertCompetencyAllUpdatablePropertiesEquals(expectedCompetency, getPersistedCompetency(expectedCompetency));
    }
}
