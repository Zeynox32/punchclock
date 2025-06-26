package ch.axa.punchclock.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ch.axa.punchclock.models.Entry;

public interface EntryRepository extends CrudRepository<Entry, Long>, EntryRepositoryCustom {
    Iterable<Entry> findByCategory(@Param("categoryId") Long categoryId);

    Iterable<Entry> findByTagsId(Long tagId);

    @Query(value = "SELECT e from Entry e WHERE e.description LIKE %:description%")
    Iterable<Entry> searchDescription(@Param("description") String description);
}
