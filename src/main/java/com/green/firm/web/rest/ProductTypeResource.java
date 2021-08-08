package com.green.firm.web.rest;

import com.green.firm.domain.ProductType;
import com.green.firm.repository.ProductTypeRepository;
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
 * REST controller for managing {@link com.green.firm.domain.ProductType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProductTypeResource.class);

    private static final String ENTITY_NAME = "productType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductTypeRepository productTypeRepository;

    public ProductTypeResource(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    /**
     * {@code POST  /product-types} : Create a new productType.
     *
     * @param productType the productType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productType, or with status {@code 400 (Bad Request)} if the productType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-types")
    public ResponseEntity<ProductType> createProductType(@Valid @RequestBody ProductType productType) throws URISyntaxException {
        log.debug("REST request to save ProductType : {}", productType);
        if (productType.getId() != null) {
            throw new BadRequestAlertException("A new productType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductType result = productTypeRepository.save(productType);
        return ResponseEntity
            .created(new URI("/api/product-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-types/:id} : Updates an existing productType.
     *
     * @param id the id of the productType to save.
     * @param productType the productType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productType,
     * or with status {@code 400 (Bad Request)} if the productType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-types/{id}")
    public ResponseEntity<ProductType> updateProductType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductType productType
    ) throws URISyntaxException {
        log.debug("REST request to update ProductType : {}, {}", id, productType);
        if (productType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductType result = productTypeRepository.save(productType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-types/:id} : Partial updates given fields of an existing productType, field will ignore if it is null
     *
     * @param id the id of the productType to save.
     * @param productType the productType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productType,
     * or with status {@code 400 (Bad Request)} if the productType is not valid,
     * or with status {@code 404 (Not Found)} if the productType is not found,
     * or with status {@code 500 (Internal Server Error)} if the productType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductType> partialUpdateProductType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductType productType
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductType partially : {}, {}", id, productType);
        if (productType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductType> result = productTypeRepository
            .findById(productType.getId())
            .map(
                existingProductType -> {
                    if (productType.getName() != null) {
                        existingProductType.setName(productType.getName());
                    }

                    return existingProductType;
                }
            )
            .map(productTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productType.getId().toString())
        );
    }

    /**
     * {@code GET  /product-types} : get all the productTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productTypes in body.
     */
    @GetMapping("/product-types")
    public ResponseEntity<List<ProductType>> getAllProductTypes(Pageable pageable) {
        log.debug("REST request to get a page of ProductTypes");
        Page<ProductType> page = productTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-types/:id} : get the "id" productType.
     *
     * @param id the id of the productType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-types/{id}")
    public ResponseEntity<ProductType> getProductType(@PathVariable Long id) {
        log.debug("REST request to get ProductType : {}", id);
        Optional<ProductType> productType = productTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productType);
    }

    /**
     * {@code DELETE  /product-types/:id} : delete the "id" productType.
     *
     * @param id the id of the productType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-types/{id}")
    public ResponseEntity<Void> deleteProductType(@PathVariable Long id) {
        log.debug("REST request to delete ProductType : {}", id);
        productTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
