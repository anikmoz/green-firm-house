package com.green.firm.web.rest;

import com.green.firm.domain.CustomerBought;
import com.green.firm.repository.CustomerBoughtRepository;
import com.green.firm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.green.firm.domain.CustomerBought}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerBoughtResource {

    private final Logger log = LoggerFactory.getLogger(CustomerBoughtResource.class);

    private static final String ENTITY_NAME = "customerBought";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerBoughtRepository customerBoughtRepository;

    public CustomerBoughtResource(CustomerBoughtRepository customerBoughtRepository) {
        this.customerBoughtRepository = customerBoughtRepository;
    }

    /**
     * {@code POST  /customer-boughts} : Create a new customerBought.
     *
     * @param customerBought the customerBought to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerBought, or with status {@code 400 (Bad Request)} if the customerBought has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-boughts")
    public ResponseEntity<CustomerBought> createCustomerBought(@Valid @RequestBody CustomerBought customerBought)
        throws URISyntaxException {
        log.debug("REST request to save CustomerBought : {}", customerBought);
        if (customerBought.getId() != null) {
            throw new BadRequestAlertException("A new customerBought cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerBought result = customerBoughtRepository.save(customerBought);
        return ResponseEntity
            .created(new URI("/api/customer-boughts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-boughts/:id} : Updates an existing customerBought.
     *
     * @param id the id of the customerBought to save.
     * @param customerBought the customerBought to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerBought,
     * or with status {@code 400 (Bad Request)} if the customerBought is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerBought couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-boughts/{id}")
    public ResponseEntity<CustomerBought> updateCustomerBought(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomerBought customerBought
    ) throws URISyntaxException {
        log.debug("REST request to update CustomerBought : {}, {}", id, customerBought);
        if (customerBought.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerBought.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerBoughtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomerBought result = customerBoughtRepository.save(customerBought);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customerBought.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customer-boughts/:id} : Partial updates given fields of an existing customerBought, field will ignore if it is null
     *
     * @param id the id of the customerBought to save.
     * @param customerBought the customerBought to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerBought,
     * or with status {@code 400 (Bad Request)} if the customerBought is not valid,
     * or with status {@code 404 (Not Found)} if the customerBought is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerBought couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customer-boughts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustomerBought> partialUpdateCustomerBought(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomerBought customerBought
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomerBought partially : {}, {}", id, customerBought);
        if (customerBought.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerBought.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerBoughtRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomerBought> result = customerBoughtRepository
            .findById(customerBought.getId())
            .map(
                existingCustomerBought -> {
                    if (customerBought.getWeightType() != null) {
                        existingCustomerBought.setWeightType(customerBought.getWeightType());
                    }
                    if (customerBought.getUnitPrice() != null) {
                        existingCustomerBought.setUnitPrice(customerBought.getUnitPrice());
                    }
                    if (customerBought.getTotalPrice() != null) {
                        existingCustomerBought.setTotalPrice(customerBought.getTotalPrice());
                    }
                    if (customerBought.getDeliveryDate() != null) {
                        existingCustomerBought.setDeliveryDate(customerBought.getDeliveryDate());
                    }
                    if (customerBought.getRemarks() != null) {
                        existingCustomerBought.setRemarks(customerBought.getRemarks());
                    }
                    if (customerBought.getStatus() != null) {
                        existingCustomerBought.setStatus(customerBought.getStatus());
                    }
                    if (customerBought.getTotalWeight() != null) {
                        existingCustomerBought.setTotalWeight(customerBought.getTotalWeight());
                    }

                    return existingCustomerBought;
                }
            )
            .map(customerBoughtRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customerBought.getId().toString())
        );
    }

    /**
     * {@code GET  /customer-boughts} : get all the customerBoughts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerBoughts in body.
     */
    @GetMapping("/customer-boughts")
    public ResponseEntity<List<CustomerBought>> getAllCustomerBoughts(Pageable pageable) {
        log.debug("REST request to get a page of CustomerBoughts");
        Page<CustomerBought> page = customerBoughtRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customer-boughts/:id} : get the "id" customerBought.
     *
     * @param id the id of the customerBought to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerBought, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-boughts/{id}")
    public ResponseEntity<CustomerBought> getCustomerBought(@PathVariable Long id) {
        log.debug("REST request to get CustomerBought : {}", id);
        Optional<CustomerBought> customerBought = customerBoughtRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerBought);
    }

    /**
     * {@code DELETE  /customer-boughts/:id} : delete the "id" customerBought.
     *
     * @param id the id of the customerBought to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-boughts/{id}")
    public ResponseEntity<Void> deleteCustomerBought(@PathVariable Long id) {
        log.debug("REST request to delete CustomerBought : {}", id);
        customerBoughtRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
