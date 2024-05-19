package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.TrainingProgramAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TrainingProgram;
import com.mycompany.myapp.repository.TrainingProgramRepository;
import com.mycompany.myapp.service.dto.TrainingProgramDTO;
import com.mycompany.myapp.service.mapper.TrainingProgramMapper;
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
 * Integration tests for the {@link TrainingProgramResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainingProgramResourceIT {

    private static final Integer DEFAULT_TRAINING_ID = 1;
    private static final Integer UPDATED_TRAINING_ID = 2;
    private static final Integer SMALLER_TRAINING_ID = 1 - 1;

    private static final String DEFAULT_TRAINING_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRAINING_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/training-programs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    @Autowired
    private TrainingProgramMapper trainingProgramMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainingProgramMockMvc;

    private TrainingProgram trainingProgram;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingProgram createEntity(EntityManager em) {
        TrainingProgram trainingProgram = new TrainingProgram()
            .trainingId(DEFAULT_TRAINING_ID)
            .trainingName(DEFAULT_TRAINING_NAME)
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return trainingProgram;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingProgram createUpdatedEntity(EntityManager em) {
        TrainingProgram trainingProgram = new TrainingProgram()
            .trainingId(UPDATED_TRAINING_ID)
            .trainingName(UPDATED_TRAINING_NAME)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return trainingProgram;
    }

    @BeforeEach
    public void initTest() {
        trainingProgram = createEntity(em);
    }

    @Test
    @Transactional
    void createTrainingProgram() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);
        var returnedTrainingProgramDTO = om.readValue(
            restTrainingProgramMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trainingProgramDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TrainingProgramDTO.class
        );

        // Validate the TrainingProgram in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTrainingProgram = trainingProgramMapper.toEntity(returnedTrainingProgramDTO);
        assertTrainingProgramUpdatableFieldsEquals(returnedTrainingProgram, getPersistedTrainingProgram(returnedTrainingProgram));
    }

    @Test
    @Transactional
    void createTrainingProgramWithExistingId() throws Exception {
        // Create the TrainingProgram with an existing ID
        trainingProgram.setId(1L);
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingProgramMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trainingProgramDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrainingPrograms() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList
        restTrainingProgramMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingProgram.getId().intValue())))
            .andExpect(jsonPath("$.[*].trainingId").value(hasItem(DEFAULT_TRAINING_ID)))
            .andExpect(jsonPath("$.[*].trainingName").value(hasItem(DEFAULT_TRAINING_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    void getTrainingProgram() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get the trainingProgram
        restTrainingProgramMockMvc
            .perform(get(ENTITY_API_URL_ID, trainingProgram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainingProgram.getId().intValue()))
            .andExpect(jsonPath("$.trainingId").value(DEFAULT_TRAINING_ID))
            .andExpect(jsonPath("$.trainingName").value(DEFAULT_TRAINING_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    void getTrainingProgramsByIdFiltering() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        Long id = trainingProgram.getId();

        defaultTrainingProgramFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTrainingProgramFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTrainingProgramFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByTrainingIdIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where trainingId equals to
        defaultTrainingProgramFiltering("trainingId.equals=" + DEFAULT_TRAINING_ID, "trainingId.equals=" + UPDATED_TRAINING_ID);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByTrainingIdIsInShouldWork() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where trainingId in
        defaultTrainingProgramFiltering(
            "trainingId.in=" + DEFAULT_TRAINING_ID + "," + UPDATED_TRAINING_ID,
            "trainingId.in=" + UPDATED_TRAINING_ID
        );
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByTrainingIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where trainingId is not null
        defaultTrainingProgramFiltering("trainingId.specified=true", "trainingId.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByTrainingIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where trainingId is greater than or equal to
        defaultTrainingProgramFiltering(
            "trainingId.greaterThanOrEqual=" + DEFAULT_TRAINING_ID,
            "trainingId.greaterThanOrEqual=" + UPDATED_TRAINING_ID
        );
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByTrainingIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where trainingId is less than or equal to
        defaultTrainingProgramFiltering(
            "trainingId.lessThanOrEqual=" + DEFAULT_TRAINING_ID,
            "trainingId.lessThanOrEqual=" + SMALLER_TRAINING_ID
        );
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByTrainingIdIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where trainingId is less than
        defaultTrainingProgramFiltering("trainingId.lessThan=" + UPDATED_TRAINING_ID, "trainingId.lessThan=" + DEFAULT_TRAINING_ID);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByTrainingIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where trainingId is greater than
        defaultTrainingProgramFiltering("trainingId.greaterThan=" + SMALLER_TRAINING_ID, "trainingId.greaterThan=" + DEFAULT_TRAINING_ID);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByTrainingNameIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where trainingName equals to
        defaultTrainingProgramFiltering("trainingName.equals=" + DEFAULT_TRAINING_NAME, "trainingName.equals=" + UPDATED_TRAINING_NAME);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByTrainingNameIsInShouldWork() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where trainingName in
        defaultTrainingProgramFiltering(
            "trainingName.in=" + DEFAULT_TRAINING_NAME + "," + UPDATED_TRAINING_NAME,
            "trainingName.in=" + UPDATED_TRAINING_NAME
        );
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByTrainingNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where trainingName is not null
        defaultTrainingProgramFiltering("trainingName.specified=true", "trainingName.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByTrainingNameContainsSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where trainingName contains
        defaultTrainingProgramFiltering("trainingName.contains=" + DEFAULT_TRAINING_NAME, "trainingName.contains=" + UPDATED_TRAINING_NAME);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByTrainingNameNotContainsSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where trainingName does not contain
        defaultTrainingProgramFiltering(
            "trainingName.doesNotContain=" + UPDATED_TRAINING_NAME,
            "trainingName.doesNotContain=" + DEFAULT_TRAINING_NAME
        );
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where startDate equals to
        defaultTrainingProgramFiltering("startDate.equals=" + DEFAULT_START_DATE, "startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where startDate in
        defaultTrainingProgramFiltering(
            "startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE,
            "startDate.in=" + UPDATED_START_DATE
        );
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where startDate is not null
        defaultTrainingProgramFiltering("startDate.specified=true", "startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where startDate is greater than or equal to
        defaultTrainingProgramFiltering(
            "startDate.greaterThanOrEqual=" + DEFAULT_START_DATE,
            "startDate.greaterThanOrEqual=" + UPDATED_START_DATE
        );
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where startDate is less than or equal to
        defaultTrainingProgramFiltering(
            "startDate.lessThanOrEqual=" + DEFAULT_START_DATE,
            "startDate.lessThanOrEqual=" + SMALLER_START_DATE
        );
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where startDate is less than
        defaultTrainingProgramFiltering("startDate.lessThan=" + UPDATED_START_DATE, "startDate.lessThan=" + DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where startDate is greater than
        defaultTrainingProgramFiltering("startDate.greaterThan=" + SMALLER_START_DATE, "startDate.greaterThan=" + DEFAULT_START_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where endDate equals to
        defaultTrainingProgramFiltering("endDate.equals=" + DEFAULT_END_DATE, "endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where endDate in
        defaultTrainingProgramFiltering("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE, "endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where endDate is not null
        defaultTrainingProgramFiltering("endDate.specified=true", "endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where endDate is greater than or equal to
        defaultTrainingProgramFiltering("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE, "endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where endDate is less than or equal to
        defaultTrainingProgramFiltering("endDate.lessThanOrEqual=" + DEFAULT_END_DATE, "endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where endDate is less than
        defaultTrainingProgramFiltering("endDate.lessThan=" + UPDATED_END_DATE, "endDate.lessThan=" + DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    void getAllTrainingProgramsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        // Get all the trainingProgramList where endDate is greater than
        defaultTrainingProgramFiltering("endDate.greaterThan=" + SMALLER_END_DATE, "endDate.greaterThan=" + DEFAULT_END_DATE);
    }

    private void defaultTrainingProgramFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTrainingProgramShouldBeFound(shouldBeFound);
        defaultTrainingProgramShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTrainingProgramShouldBeFound(String filter) throws Exception {
        restTrainingProgramMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingProgram.getId().intValue())))
            .andExpect(jsonPath("$.[*].trainingId").value(hasItem(DEFAULT_TRAINING_ID)))
            .andExpect(jsonPath("$.[*].trainingName").value(hasItem(DEFAULT_TRAINING_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));

        // Check, that the count call also returns 1
        restTrainingProgramMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTrainingProgramShouldNotBeFound(String filter) throws Exception {
        restTrainingProgramMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrainingProgramMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTrainingProgram() throws Exception {
        // Get the trainingProgram
        restTrainingProgramMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrainingProgram() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trainingProgram
        TrainingProgram updatedTrainingProgram = trainingProgramRepository.findById(trainingProgram.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTrainingProgram are not directly saved in db
        em.detach(updatedTrainingProgram);
        updatedTrainingProgram
            .trainingId(UPDATED_TRAINING_ID)
            .trainingName(UPDATED_TRAINING_NAME)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(updatedTrainingProgram);

        restTrainingProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingProgramDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trainingProgramDTO))
            )
            .andExpect(status().isOk());

        // Validate the TrainingProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTrainingProgramToMatchAllProperties(updatedTrainingProgram);
    }

    @Test
    @Transactional
    void putNonExistingTrainingProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trainingProgram.setId(longCount.incrementAndGet());

        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainingProgramDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrainingProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trainingProgram.setId(longCount.incrementAndGet());

        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrainingProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trainingProgram.setId(longCount.incrementAndGet());

        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingProgramMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trainingProgramDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainingProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainingProgramWithPatch() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trainingProgram using partial update
        TrainingProgram partialUpdatedTrainingProgram = new TrainingProgram();
        partialUpdatedTrainingProgram.setId(trainingProgram.getId());

        restTrainingProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainingProgram.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrainingProgram))
            )
            .andExpect(status().isOk());

        // Validate the TrainingProgram in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrainingProgramUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTrainingProgram, trainingProgram),
            getPersistedTrainingProgram(trainingProgram)
        );
    }

    @Test
    @Transactional
    void fullUpdateTrainingProgramWithPatch() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trainingProgram using partial update
        TrainingProgram partialUpdatedTrainingProgram = new TrainingProgram();
        partialUpdatedTrainingProgram.setId(trainingProgram.getId());

        partialUpdatedTrainingProgram
            .trainingId(UPDATED_TRAINING_ID)
            .trainingName(UPDATED_TRAINING_NAME)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restTrainingProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainingProgram.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrainingProgram))
            )
            .andExpect(status().isOk());

        // Validate the TrainingProgram in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrainingProgramUpdatableFieldsEquals(
            partialUpdatedTrainingProgram,
            getPersistedTrainingProgram(partialUpdatedTrainingProgram)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTrainingProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trainingProgram.setId(longCount.incrementAndGet());

        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainingProgramDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrainingProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trainingProgram.setId(longCount.incrementAndGet());

        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trainingProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainingProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrainingProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trainingProgram.setId(longCount.incrementAndGet());

        // Create the TrainingProgram
        TrainingProgramDTO trainingProgramDTO = trainingProgramMapper.toDto(trainingProgram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainingProgramMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(trainingProgramDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainingProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrainingProgram() throws Exception {
        // Initialize the database
        trainingProgramRepository.saveAndFlush(trainingProgram);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the trainingProgram
        restTrainingProgramMockMvc
            .perform(delete(ENTITY_API_URL_ID, trainingProgram.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return trainingProgramRepository.count();
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

    protected TrainingProgram getPersistedTrainingProgram(TrainingProgram trainingProgram) {
        return trainingProgramRepository.findById(trainingProgram.getId()).orElseThrow();
    }

    protected void assertPersistedTrainingProgramToMatchAllProperties(TrainingProgram expectedTrainingProgram) {
        assertTrainingProgramAllPropertiesEquals(expectedTrainingProgram, getPersistedTrainingProgram(expectedTrainingProgram));
    }

    protected void assertPersistedTrainingProgramToMatchUpdatableProperties(TrainingProgram expectedTrainingProgram) {
        assertTrainingProgramAllUpdatablePropertiesEquals(expectedTrainingProgram, getPersistedTrainingProgram(expectedTrainingProgram));
    }
}
