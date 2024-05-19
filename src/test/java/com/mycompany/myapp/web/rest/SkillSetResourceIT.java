package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.SkillSetAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.SkillSet;
import com.mycompany.myapp.domain.enumeration.ProficiencyLevel;
import com.mycompany.myapp.repository.SkillSetRepository;
import com.mycompany.myapp.service.dto.SkillSetDTO;
import com.mycompany.myapp.service.mapper.SkillSetMapper;
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
 * Integration tests for the {@link SkillSetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SkillSetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ProficiencyLevel DEFAULT_PROFIECIENCY_LEVEL = ProficiencyLevel.BEGINNER;
    private static final ProficiencyLevel UPDATED_PROFIECIENCY_LEVEL = ProficiencyLevel.INTERMEDIATE;

    private static final LocalDate DEFAULT_LAST_ASSESSED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_ASSESSED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LAST_ASSESSED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/skill-sets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SkillSetRepository skillSetRepository;

    @Autowired
    private SkillSetMapper skillSetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSkillSetMockMvc;

    private SkillSet skillSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillSet createEntity(EntityManager em) {
        SkillSet skillSet = new SkillSet()
            .name(DEFAULT_NAME)
            .profieciencyLevel(DEFAULT_PROFIECIENCY_LEVEL)
            .lastAssessedDate(DEFAULT_LAST_ASSESSED_DATE);
        return skillSet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillSet createUpdatedEntity(EntityManager em) {
        SkillSet skillSet = new SkillSet()
            .name(UPDATED_NAME)
            .profieciencyLevel(UPDATED_PROFIECIENCY_LEVEL)
            .lastAssessedDate(UPDATED_LAST_ASSESSED_DATE);
        return skillSet;
    }

    @BeforeEach
    public void initTest() {
        skillSet = createEntity(em);
    }

    @Test
    @Transactional
    void createSkillSet() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SkillSet
        SkillSetDTO skillSetDTO = skillSetMapper.toDto(skillSet);
        var returnedSkillSetDTO = om.readValue(
            restSkillSetMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(skillSetDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SkillSetDTO.class
        );

        // Validate the SkillSet in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSkillSet = skillSetMapper.toEntity(returnedSkillSetDTO);
        assertSkillSetUpdatableFieldsEquals(returnedSkillSet, getPersistedSkillSet(returnedSkillSet));
    }

    @Test
    @Transactional
    void createSkillSetWithExistingId() throws Exception {
        // Create the SkillSet with an existing ID
        skillSet.setId(1L);
        SkillSetDTO skillSetDTO = skillSetMapper.toDto(skillSet);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(skillSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SkillSet in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSkillSets() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList
        restSkillSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].profieciencyLevel").value(hasItem(DEFAULT_PROFIECIENCY_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].lastAssessedDate").value(hasItem(DEFAULT_LAST_ASSESSED_DATE.toString())));
    }

    @Test
    @Transactional
    void getSkillSet() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get the skillSet
        restSkillSetMockMvc
            .perform(get(ENTITY_API_URL_ID, skillSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(skillSet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.profieciencyLevel").value(DEFAULT_PROFIECIENCY_LEVEL.toString()))
            .andExpect(jsonPath("$.lastAssessedDate").value(DEFAULT_LAST_ASSESSED_DATE.toString()));
    }

    @Test
    @Transactional
    void getSkillSetsByIdFiltering() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        Long id = skillSet.getId();

        defaultSkillSetFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSkillSetFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSkillSetFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSkillSetsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where name equals to
        defaultSkillSetFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSkillSetsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where name in
        defaultSkillSetFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSkillSetsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where name is not null
        defaultSkillSetFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillSetsByNameContainsSomething() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where name contains
        defaultSkillSetFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSkillSetsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where name does not contain
        defaultSkillSetFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllSkillSetsByProfieciencyLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where profieciencyLevel equals to
        defaultSkillSetFiltering(
            "profieciencyLevel.equals=" + DEFAULT_PROFIECIENCY_LEVEL,
            "profieciencyLevel.equals=" + UPDATED_PROFIECIENCY_LEVEL
        );
    }

    @Test
    @Transactional
    void getAllSkillSetsByProfieciencyLevelIsInShouldWork() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where profieciencyLevel in
        defaultSkillSetFiltering(
            "profieciencyLevel.in=" + DEFAULT_PROFIECIENCY_LEVEL + "," + UPDATED_PROFIECIENCY_LEVEL,
            "profieciencyLevel.in=" + UPDATED_PROFIECIENCY_LEVEL
        );
    }

    @Test
    @Transactional
    void getAllSkillSetsByProfieciencyLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where profieciencyLevel is not null
        defaultSkillSetFiltering("profieciencyLevel.specified=true", "profieciencyLevel.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillSetsByLastAssessedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where lastAssessedDate equals to
        defaultSkillSetFiltering(
            "lastAssessedDate.equals=" + DEFAULT_LAST_ASSESSED_DATE,
            "lastAssessedDate.equals=" + UPDATED_LAST_ASSESSED_DATE
        );
    }

    @Test
    @Transactional
    void getAllSkillSetsByLastAssessedDateIsInShouldWork() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where lastAssessedDate in
        defaultSkillSetFiltering(
            "lastAssessedDate.in=" + DEFAULT_LAST_ASSESSED_DATE + "," + UPDATED_LAST_ASSESSED_DATE,
            "lastAssessedDate.in=" + UPDATED_LAST_ASSESSED_DATE
        );
    }

    @Test
    @Transactional
    void getAllSkillSetsByLastAssessedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where lastAssessedDate is not null
        defaultSkillSetFiltering("lastAssessedDate.specified=true", "lastAssessedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillSetsByLastAssessedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where lastAssessedDate is greater than or equal to
        defaultSkillSetFiltering(
            "lastAssessedDate.greaterThanOrEqual=" + DEFAULT_LAST_ASSESSED_DATE,
            "lastAssessedDate.greaterThanOrEqual=" + UPDATED_LAST_ASSESSED_DATE
        );
    }

    @Test
    @Transactional
    void getAllSkillSetsByLastAssessedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where lastAssessedDate is less than or equal to
        defaultSkillSetFiltering(
            "lastAssessedDate.lessThanOrEqual=" + DEFAULT_LAST_ASSESSED_DATE,
            "lastAssessedDate.lessThanOrEqual=" + SMALLER_LAST_ASSESSED_DATE
        );
    }

    @Test
    @Transactional
    void getAllSkillSetsByLastAssessedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where lastAssessedDate is less than
        defaultSkillSetFiltering(
            "lastAssessedDate.lessThan=" + UPDATED_LAST_ASSESSED_DATE,
            "lastAssessedDate.lessThan=" + DEFAULT_LAST_ASSESSED_DATE
        );
    }

    @Test
    @Transactional
    void getAllSkillSetsByLastAssessedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        // Get all the skillSetList where lastAssessedDate is greater than
        defaultSkillSetFiltering(
            "lastAssessedDate.greaterThan=" + SMALLER_LAST_ASSESSED_DATE,
            "lastAssessedDate.greaterThan=" + DEFAULT_LAST_ASSESSED_DATE
        );
    }

    @Test
    @Transactional
    void getAllSkillSetsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            skillSetRepository.saveAndFlush(skillSet);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        skillSet.addEmployee(employee);
        skillSetRepository.saveAndFlush(skillSet);
        Long employeeId = employee.getId();
        // Get all the skillSetList where employee equals to employeeId
        defaultSkillSetShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the skillSetList where employee equals to (employeeId + 1)
        defaultSkillSetShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    private void defaultSkillSetFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSkillSetShouldBeFound(shouldBeFound);
        defaultSkillSetShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSkillSetShouldBeFound(String filter) throws Exception {
        restSkillSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].profieciencyLevel").value(hasItem(DEFAULT_PROFIECIENCY_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].lastAssessedDate").value(hasItem(DEFAULT_LAST_ASSESSED_DATE.toString())));

        // Check, that the count call also returns 1
        restSkillSetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSkillSetShouldNotBeFound(String filter) throws Exception {
        restSkillSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSkillSetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSkillSet() throws Exception {
        // Get the skillSet
        restSkillSetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSkillSet() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the skillSet
        SkillSet updatedSkillSet = skillSetRepository.findById(skillSet.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSkillSet are not directly saved in db
        em.detach(updatedSkillSet);
        updatedSkillSet.name(UPDATED_NAME).profieciencyLevel(UPDATED_PROFIECIENCY_LEVEL).lastAssessedDate(UPDATED_LAST_ASSESSED_DATE);
        SkillSetDTO skillSetDTO = skillSetMapper.toDto(updatedSkillSet);

        restSkillSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skillSetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(skillSetDTO))
            )
            .andExpect(status().isOk());

        // Validate the SkillSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSkillSetToMatchAllProperties(updatedSkillSet);
    }

    @Test
    @Transactional
    void putNonExistingSkillSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        skillSet.setId(longCount.incrementAndGet());

        // Create the SkillSet
        SkillSetDTO skillSetDTO = skillSetMapper.toDto(skillSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skillSetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(skillSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSkillSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        skillSet.setId(longCount.incrementAndGet());

        // Create the SkillSet
        SkillSetDTO skillSetDTO = skillSetMapper.toDto(skillSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(skillSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSkillSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        skillSet.setId(longCount.incrementAndGet());

        // Create the SkillSet
        SkillSetDTO skillSetDTO = skillSetMapper.toDto(skillSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillSetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(skillSetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SkillSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSkillSetWithPatch() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the skillSet using partial update
        SkillSet partialUpdatedSkillSet = new SkillSet();
        partialUpdatedSkillSet.setId(skillSet.getId());

        restSkillSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkillSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSkillSet))
            )
            .andExpect(status().isOk());

        // Validate the SkillSet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSkillSetUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSkillSet, skillSet), getPersistedSkillSet(skillSet));
    }

    @Test
    @Transactional
    void fullUpdateSkillSetWithPatch() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the skillSet using partial update
        SkillSet partialUpdatedSkillSet = new SkillSet();
        partialUpdatedSkillSet.setId(skillSet.getId());

        partialUpdatedSkillSet
            .name(UPDATED_NAME)
            .profieciencyLevel(UPDATED_PROFIECIENCY_LEVEL)
            .lastAssessedDate(UPDATED_LAST_ASSESSED_DATE);

        restSkillSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkillSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSkillSet))
            )
            .andExpect(status().isOk());

        // Validate the SkillSet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSkillSetUpdatableFieldsEquals(partialUpdatedSkillSet, getPersistedSkillSet(partialUpdatedSkillSet));
    }

    @Test
    @Transactional
    void patchNonExistingSkillSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        skillSet.setId(longCount.incrementAndGet());

        // Create the SkillSet
        SkillSetDTO skillSetDTO = skillSetMapper.toDto(skillSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, skillSetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(skillSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSkillSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        skillSet.setId(longCount.incrementAndGet());

        // Create the SkillSet
        SkillSetDTO skillSetDTO = skillSetMapper.toDto(skillSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(skillSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSkillSet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        skillSet.setId(longCount.incrementAndGet());

        // Create the SkillSet
        SkillSetDTO skillSetDTO = skillSetMapper.toDto(skillSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillSetMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(skillSetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SkillSet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSkillSet() throws Exception {
        // Initialize the database
        skillSetRepository.saveAndFlush(skillSet);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the skillSet
        restSkillSetMockMvc
            .perform(delete(ENTITY_API_URL_ID, skillSet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return skillSetRepository.count();
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

    protected SkillSet getPersistedSkillSet(SkillSet skillSet) {
        return skillSetRepository.findById(skillSet.getId()).orElseThrow();
    }

    protected void assertPersistedSkillSetToMatchAllProperties(SkillSet expectedSkillSet) {
        assertSkillSetAllPropertiesEquals(expectedSkillSet, getPersistedSkillSet(expectedSkillSet));
    }

    protected void assertPersistedSkillSetToMatchUpdatableProperties(SkillSet expectedSkillSet) {
        assertSkillSetAllUpdatablePropertiesEquals(expectedSkillSet, getPersistedSkillSet(expectedSkillSet));
    }
}
