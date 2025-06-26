package ch.axa.punchclock.repositories;

import org.springframework.data.repository.CrudRepository;

import ch.axa.punchclock.models.Tags;

public interface TagRepository extends CrudRepository<Tags, Long>{}