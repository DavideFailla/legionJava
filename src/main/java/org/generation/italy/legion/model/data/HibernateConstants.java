package org.generation.italy.legion.model.data;

public class HibernateConstants {

    public static final String HQL_DEACTIVATE_OLDEST_N_COURSES = """
               update Course c set c.isActive=false where c in (
               select co from Course co where co.isActive = true
               order by co.createdAt
               limit :limit
            )
            """;

    public static final String HQL_OLDEST_N_COURSES = """
            from Course c
            where c.isActive=true
            order by c.createdAt
            limit :limit
            """;
    public static final String HQL_FIND_COURSE_BY_TITLE_LIKE= """
                     FROM Course c
                     WHERE c.title LIKE :part
            """;
    public static final String HQL_FIND_BY_TITLE_LIKE_WHEN_ACTIVE_AND_MIN_EDITION = """
                     FROM Course c
                     WHERE c.title LIKE :part AND c.active = :status AND size(c.editions) >= :minEditions
            """;

    public static final String HQL_FIND_BY_TITLE_LIKE_WHEN_ACTIVE = """
                     FROM Course c
                     WHERE c.title LIKE :part AND c.active = :status
            """;
    public static final String HQL_FIND_COURSE_BY_TITLE_LIKE_AND_MIN_EDITION = """
                     FROM Course c
                     WHERE c.title LIKE :part AND size(c.editions) >= :minEditions
            """;
    public static final String HQL_FIND_TEACHER_BY_LEVEL = """
            from Teacher t
            where t.level = :level
            """;
//    public static final String HQL_FIND_TEACHER_BY_SKILL_LEVEL = """
//           from Teacher t
//           where t.id in (select c.person.id from Competence c where c.level = :level and
//                            c.skill.id = :id)
//           """;
    public static final String HQL_FIND_TEACHER_BY_SKILL_LEVEL = """
           from Teacher t
           where exists(
                from Competence co
                where co.level = :level and
                      co.person = t and
                      co.skill.id = :id)
           """;
//public static final String HQL_FIND_TEACHER_BY_SKILL_LEVEL = """
//           from Teacher t
//           join t.competences comp
//           where comp.skill.id = :id
//                 and comp.level = :level
//           """;

//    SELECT t.id_teacher, p.firstname, p.lastname
//    FROM teacher AS t
//    JOIN person AS p
//    ON t.id_teacher = p.id_person
//    WHERE t.id_teacher IN (SELECT id_teacher
//						FROM edition_module
//                        GROUP BY id_teacher
//                        HAVING COUNT (*) = 3)

public static final String HQL_FIND_TEACHERS_BY_COURSE_EDITION = """
       from Teacher t
       where t in (
                   select m.teacher
                   from EditionModule m
                   group by m.teacher
                   having count (*) = :n)
       """;

public static final String HQL_FIND_MODE_COURSE_EDITION_COST = """
        select ce.cost
        from CourseEdition ce
        group by ce.cost
        order by count(ce.id) desc
       
        """;
    public static final String HQL_COUNT_COURSE_EDITION = """
       select count(ce.id)
       from CourseEdition ce
       """;
    public static final String HQL_GET_ALL_COURSE_EDITION = """
       select ce
       from CourseEdition ce
       order by (ce.cost)
       """;
}







