package com.project.wab.repository;

import com.project.wab.domain.Cart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    @Query(value = "SELECT c FROM Cart c WHERE c.userId = :userId")
    Optional<Cart> findCartByUserID(Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Cart c WHERE c.id = :cartId")
    void deleteCartById(UUID cartId);

    default Map<String, Object> mergeAndDeleteCart(
            EntityManager em,
            @Param("p_source_cart_id") String sourceCartId,
            @Param("p_user_id") Long userId) {
        StoredProcedureQuery query = em.createStoredProcedureQuery("merge_and_delete_cart");
        query.registerStoredProcedureParameter("p_source_cart_id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cart_id", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("p_total", Long.class, ParameterMode.OUT);

        query.setParameter("p_source_cart_id", sourceCartId);
        query.setParameter("p_user_id", userId);
        query.execute();
        Map<String, Object> result = new HashMap<>();
        result.put("p_cart_id", query.getOutputParameterValue("p_cart_id"));
        result.put("p_total", query.getOutputParameterValue("p_total"));

        return result;
    }
}
