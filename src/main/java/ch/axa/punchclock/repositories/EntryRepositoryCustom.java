package ch.axa.punchclock.repositories;

import ch.axa.punchclock.models.Entry;

public interface EntryRepositoryCustom {
    Iterable<Entry> searchDescriptionByWord(String word);
}
