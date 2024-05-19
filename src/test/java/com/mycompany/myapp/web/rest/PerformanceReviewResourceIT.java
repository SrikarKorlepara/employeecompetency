package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.PerformanceReviewAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.PerformanceReview;
import com.mycompany.myapp.repository.PerformanceReviewRepository;
import com.mycompany.myapp.service.dto.PerformanceReviewDTO;
import com.mycompany.myapp.service.mapper.PerformanceReviewMapper;
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
 * Integration tests for the {@link PerformanceReviewResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PerformanceReviewResourceIT {

    private static final Integer DEFAULT_REVIEW_ID = 1;
    private static final Integer UPDATED_REVIEW_ID = 2;
    private static final Integer SMALLER_REVIEW_ID = 1 - 1;

    private static final LocalDate DEFAULT_REVIEW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVIEW_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REVIEW_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_OVERALL_RATING = "AAAAAAAAAA";
    private static final String UPDATED_OVERALL_RATING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/performance-reviews";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;

    @Autowired
    private PerformanceReviewMapper performanceReviewMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerformanceReviewMockMvc;

    private PerformanceReview performanceReview;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerformanceReview createEntity(EntityManager em) {
        PerformanceReview performanceReview = new PerformanceReview()
            .reviewId(DEFAULT_REVIEW_ID)
            .reviewDate(DEFAULT_REVIEW_DATE)
            .comments(DEFAULT_COMMENTS)
            .overallRating(DEFAULT_OVERALL_RATING);
        return performanceReview;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerformanceReview createUpdatedEntity(EntityManager em) {
        PerformanceReview performanceReview = new PerformanceReview()
            .reviewId(UPDATED_REVIEW_ID)
            .reviewDate(UPDATED_REVIEW_DATE)
            .comments(UPDATED_COMMENTS)
            .overallRating(UPDATED_OVERALL_RATING);
        return performanceReview;
    }

    @BeforeEach
    public void initTest() {
        performanceReview = createEntity(em);
    }

    @Test
    @Transactional
    void createPerformanceReview() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);
        var returnedPerformanceReviewDTO = om.readValue(
            restPerformanceReviewMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(performanceReviewDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PerformanceReviewDTO.class
        );

        // Validate the PerformanceReview in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPerformanceReview = performanceReviewMapper.toEntity(returnedPerformanceReviewDTO);
        assertPerformanceReviewUpdatableFieldsEquals(returnedPerformanceReview, getPersistedPerformanceReview(returnedPerformanceReview));
    }

    @Test
    @Transactional
    void createPerformanceReviewWithExistingId() throws Exception {
        // Create the PerformanceReview with an existing ID
        performanceReview.setId(1L);
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerformanceReviewMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(performanceReviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PerformanceReview in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPerformanceReviews() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList
        restPerformanceReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(performanceReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].reviewId").value(hasItem(DEFAULT_REVIEW_ID)))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].overallRating").value(hasItem(DEFAULT_OVERALL_RATING)));
    }

    @Test
    @Transactional
    void getPerformanceReview() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get the performanceReview
        restPerformanceReviewMockMvc
            .perform(get(ENTITY_API_URL_ID, performanceReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(performanceReview.getId().intValue()))
            .andExpect(jsonPath("$.reviewId").value(DEFAULT_REVIEW_ID))
            .andExpect(jsonPath("$.reviewDate").value(DEFAULT_REVIEW_DATE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.overallRating").value(DEFAULT_OVERALL_RATING));
    }

    @Test
    @Transactional
    void getPerformanceReviewsByIdFiltering() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        Long id = performanceReview.getId();

        defaultPerformanceReviewFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPerformanceReviewFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPerformanceReviewFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewIdIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewId equals to
        defaultPerformanceReviewFiltering("reviewId.equals=" + DEFAULT_REVIEW_ID, "reviewId.equals=" + UPDATED_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewIdIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewId in
        defaultPerformanceReviewFiltering("reviewId.in=" + DEFAULT_REVIEW_ID + "," + UPDATED_REVIEW_ID, "reviewId.in=" + UPDATED_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewId is not null
        defaultPerformanceReviewFiltering("reviewId.specified=true", "reviewId.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewId is greater than or equal to
        defaultPerformanceReviewFiltering(
            "reviewId.greaterThanOrEqual=" + DEFAULT_REVIEW_ID,
            "reviewId.greaterThanOrEqual=" + UPDATED_REVIEW_ID
        );
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewId is less than or equal to
        defaultPerformanceReviewFiltering("reviewId.lessThanOrEqual=" + DEFAULT_REVIEW_ID, "reviewId.lessThanOrEqual=" + SMALLER_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewIdIsLessThanSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewId is less than
        defaultPerformanceReviewFiltering("reviewId.lessThan=" + UPDATED_REVIEW_ID, "reviewId.lessThan=" + DEFAULT_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewId is greater than
        defaultPerformanceReviewFiltering("reviewId.greaterThan=" + SMALLER_REVIEW_ID, "reviewId.greaterThan=" + DEFAULT_REVIEW_ID);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewDateIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewDate equals to
        defaultPerformanceReviewFiltering("reviewDate.equals=" + DEFAULT_REVIEW_DATE, "reviewDate.equals=" + UPDATED_REVIEW_DATE);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewDateIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewDate in
        defaultPerformanceReviewFiltering(
            "reviewDate.in=" + DEFAULT_REVIEW_DATE + "," + UPDATED_REVIEW_DATE,
            "reviewDate.in=" + UPDATED_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewDate is not null
        defaultPerformanceReviewFiltering("reviewDate.specified=true", "reviewDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewDate is greater than or equal to
        defaultPerformanceReviewFiltering(
            "reviewDate.greaterThanOrEqual=" + DEFAULT_REVIEW_DATE,
            "reviewDate.greaterThanOrEqual=" + UPDATED_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewDate is less than or equal to
        defaultPerformanceReviewFiltering(
            "reviewDate.lessThanOrEqual=" + DEFAULT_REVIEW_DATE,
            "reviewDate.lessThanOrEqual=" + SMALLER_REVIEW_DATE
        );
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewDateIsLessThanSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewDate is less than
        defaultPerformanceReviewFiltering("reviewDate.lessThan=" + UPDATED_REVIEW_DATE, "reviewDate.lessThan=" + DEFAULT_REVIEW_DATE);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where reviewDate is greater than
        defaultPerformanceReviewFiltering("reviewDate.greaterThan=" + SMALLER_REVIEW_DATE, "reviewDate.greaterThan=" + DEFAULT_REVIEW_DATE);
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByOverallRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where overallRating equals to
        defaultPerformanceReviewFiltering(
            "overallRating.equals=" + DEFAULT_OVERALL_RATING,
            "overallRating.equals=" + UPDATED_OVERALL_RATING
        );
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByOverallRatingIsInShouldWork() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where overallRating in
        defaultPerformanceReviewFiltering(
            "overallRating.in=" + DEFAULT_OVERALL_RATING + "," + UPDATED_OVERALL_RATING,
            "overallRating.in=" + UPDATED_OVERALL_RATING
        );
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByOverallRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where overallRating is not null
        defaultPerformanceReviewFiltering("overallRating.specified=true", "overallRating.specified=false");
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByOverallRatingContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where overallRating contains
        defaultPerformanceReviewFiltering(
            "overallRating.contains=" + DEFAULT_OVERALL_RATING,
            "overallRating.contains=" + UPDATED_OVERALL_RATING
        );
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByOverallRatingNotContainsSomething() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        // Get all the performanceReviewList where overallRating does not contain
        defaultPerformanceReviewFiltering(
            "overallRating.doesNotContain=" + UPDATED_OVERALL_RATING,
            "overallRating.doesNotContain=" + DEFAULT_OVERALL_RATING
        );
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            performanceReviewRepository.saveAndFlush(performanceReview);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        performanceReview.setEmployee(employee);
        performanceReviewRepository.saveAndFlush(performanceReview);
        Long employeeId = employee.getId();
        // Get all the performanceReviewList where employee equals to employeeId
        defaultPerformanceReviewShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the performanceReviewList where employee equals to (employeeId + 1)
        defaultPerformanceReviewShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllPerformanceReviewsByReviewerIsEqualToSomething() throws Exception {
        Employee reviewer;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            performanceReviewRepository.saveAndFlush(performanceReview);
            reviewer = EmployeeResourceIT.createEntity(em);
        } else {
            reviewer = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(reviewer);
        em.flush();
        performanceReview.setReviewer(reviewer);
        performanceReviewRepository.saveAndFlush(performanceReview);
        Long reviewerId = reviewer.getId();
        // Get all the performanceReviewList where reviewer equals to reviewerId
        defaultPerformanceReviewShouldBeFound("reviewerId.equals=" + reviewerId);

        // Get all the performanceReviewList where reviewer equals to (reviewerId + 1)
        defaultPerformanceReviewShouldNotBeFound("reviewerId.equals=" + (reviewerId + 1));
    }

    private void defaultPerformanceReviewFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPerformanceReviewShouldBeFound(shouldBeFound);
        defaultPerformanceReviewShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerformanceReviewShouldBeFound(String filter) throws Exception {
        restPerformanceReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(performanceReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].reviewId").value(hasItem(DEFAULT_REVIEW_ID)))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].overallRating").value(hasItem(DEFAULT_OVERALL_RATING)));

        // Check, that the count call also returns 1
        restPerformanceReviewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerformanceReviewShouldNotBeFound(String filter) throws Exception {
        restPerformanceReviewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerformanceReviewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPerformanceReview() throws Exception {
        // Get the performanceReview
        restPerformanceReviewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerformanceReview() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the performanceReview
        PerformanceReview updatedPerformanceReview = performanceReviewRepository.findById(performanceReview.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPerformanceReview are not directly saved in db
        em.detach(updatedPerformanceReview);
        updatedPerformanceReview
            .reviewId(UPDATED_REVIEW_ID)
            .reviewDate(UPDATED_REVIEW_DATE)
            .comments(UPDATED_COMMENTS)
            .overallRating(UPDATED_OVERALL_RATING);
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(updatedPerformanceReview);

        restPerformanceReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, performanceReviewDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(performanceReviewDTO))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPerformanceReviewToMatchAllProperties(updatedPerformanceReview);
    }

    @Test
    @Transactional
    void putNonExistingPerformanceReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        performanceReview.setId(longCount.incrementAndGet());

        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerformanceReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, performanceReviewDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(performanceReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerformanceReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        performanceReview.setId(longCount.incrementAndGet());

        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceReviewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(performanceReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerformanceReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        performanceReview.setId(longCount.incrementAndGet());

        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceReviewMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(performanceReviewDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerformanceReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePerformanceReviewWithPatch() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the performanceReview using partial update
        PerformanceReview partialUpdatedPerformanceReview = new PerformanceReview();
        partialUpdatedPerformanceReview.setId(performanceReview.getId());

        partialUpdatedPerformanceReview.reviewId(UPDATED_REVIEW_ID).reviewDate(UPDATED_REVIEW_DATE).comments(UPDATED_COMMENTS);

        restPerformanceReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerformanceReview.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerformanceReview))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceReview in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerformanceReviewUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPerformanceReview, performanceReview),
            getPersistedPerformanceReview(performanceReview)
        );
    }

    @Test
    @Transactional
    void fullUpdatePerformanceReviewWithPatch() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the performanceReview using partial update
        PerformanceReview partialUpdatedPerformanceReview = new PerformanceReview();
        partialUpdatedPerformanceReview.setId(performanceReview.getId());

        partialUpdatedPerformanceReview
            .reviewId(UPDATED_REVIEW_ID)
            .reviewDate(UPDATED_REVIEW_DATE)
            .comments(UPDATED_COMMENTS)
            .overallRating(UPDATED_OVERALL_RATING);

        restPerformanceReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerformanceReview.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerformanceReview))
            )
            .andExpect(status().isOk());

        // Validate the PerformanceReview in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerformanceReviewUpdatableFieldsEquals(
            partialUpdatedPerformanceReview,
            getPersistedPerformanceReview(partialUpdatedPerformanceReview)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPerformanceReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        performanceReview.setId(longCount.incrementAndGet());

        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerformanceReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, performanceReviewDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(performanceReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerformanceReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        performanceReview.setId(longCount.incrementAndGet());

        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceReviewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(performanceReviewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerformanceReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerformanceReview() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        performanceReview.setId(longCount.incrementAndGet());

        // Create the PerformanceReview
        PerformanceReviewDTO performanceReviewDTO = performanceReviewMapper.toDto(performanceReview);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerformanceReviewMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(performanceReviewDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerformanceReview in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerformanceReview() throws Exception {
        // Initialize the database
        performanceReviewRepository.saveAndFlush(performanceReview);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the performanceReview
        restPerformanceReviewMockMvc
            .perform(delete(ENTITY_API_URL_ID, performanceReview.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return performanceReviewRepository.count();
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

    protected PerformanceReview getPersistedPerformanceReview(PerformanceReview performanceReview) {
        return performanceReviewRepository.findById(performanceReview.getId()).orElseThrow();
    }

    protected void assertPersistedPerformanceReviewToMatchAllProperties(PerformanceReview expectedPerformanceReview) {
        assertPerformanceReviewAllPropertiesEquals(expectedPerformanceReview, getPersistedPerformanceReview(expectedPerformanceReview));
    }

    protected void assertPersistedPerformanceReviewToMatchUpdatableProperties(PerformanceReview expectedPerformanceReview) {
        assertPerformanceReviewAllUpdatablePropertiesEquals(
            expectedPerformanceReview,
            getPersistedPerformanceReview(expectedPerformanceReview)
        );
    }
}
