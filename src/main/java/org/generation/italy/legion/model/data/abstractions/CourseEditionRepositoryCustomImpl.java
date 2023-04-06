package org.generation.italy.legion.model.data.abstractions;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.generation.italy.legion.model.data.abstractions.CourseEditionRepositoryCustom;
import org.generation.italy.legion.model.entities.CourseEdition;

import java.util.ArrayList;
import java.util.Optional;

import static org.generation.italy.legion.model.data.HibernateConstants.*;

public class CourseEditionRepositoryCustomImpl implements CourseEditionRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
//    @Override
//    public Iterable<CourseEdition> findMedian() {
//
//        Query query = entityManager.createQuery("SELECT * FROM CourseEditions");
//        List<CourseEdition> lCE = query.getResultList();
//
//        if(lCE.size()%2 == 0) {
//            return entityManager.createQuery("""
//                        SELECT *
//                        FROM course_edition
//                        WHERE price = (SELECT PERCENTILE_CONT(0.6) WITHIN GROUP (ORDER BY price)
//                                            FROM course_edition as ce)""").getResultList();
//        }
//        return entityManager.createQuery("""
//                        SELECT *
//                        FROM course_edition
//                        WHERE price = (SELECT PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY price)
//                                            FROM course_edition as ce)""").getResultList();
//
//
//    }
//    @Override
//    public Iterable<CourseEdition> findMedian() {
//
//        Query query = entityManager.createQuery("SELECT * FROM CourseEditions");
//        List<CourseEdition> lCE = query.getResultList();
//
//        if(lCE.size()%2 == 0) {
//            return entityManager.createQuery("""
//                        SELECT *
//                        (SELECT MAX(cost) FROM
//                             (SELECT TOP 60 PERCENT ce.cost FROM course_edition as ce  ORDER BY ce.cost)
//                                             )""").getResultList();
//        }
//        return entityManager.createQuery("""
//                        SELECT *
//                        (SELECT MAX(cost) FROM
//                             (SELECT TOP 50 PERCENT ce.cost FROM course_edition as ce  ORDER BY ce.cost)
//                                             )""").getResultList();
//
//
//    }

//    @Override
//    public Iterable<CourseEdition> findMedianCourseEdition() {
//        return entityManager.createQuery("""
//                        SELECT
//                        (SELECT MAX(cost) FROM
//                             (SELECT TOP 50 PERCENT ce.cost FROM course_edition as ce  ORDER BY ce.cost)
//                                             )""").getResultList();
//
//
//    }

    @Override
    public Iterable<CourseEdition> findMedian() {
        TypedQuery<Long> qCountCE = entityManager.createQuery(HQL_COUNT_COURSE_EDITION, Long.class);
        int countCe= (int) qCountCE.getSingleResult().longValue();
//         int countCe = 0;
        if(countCe == 0){
            return new ArrayList<CourseEdition>();
        }
        TypedQuery<CourseEdition> qAllCourseEdition = entityManager.createQuery(HQL_GET_ALL_COURSE_EDITION, CourseEdition.class);
        if(countCe % 2 != 0){
            return qAllCourseEdition.setFirstResult(countCe / 2).setMaxResults(1).getResultList();
        }
        return qAllCourseEdition.setFirstResult(countCe / 2 - 1).setMaxResults(2).getResultList();
    }

    @Override
    public Optional<Double> getCourseEditionCostMode() {
        TypedQuery<Double> q = entityManager.createQuery(HQL_FIND_MODE_COURSE_EDITION_COST, Double.class);
        return q.setMaxResults(1).getResultList().stream().findFirst();
    }


}
