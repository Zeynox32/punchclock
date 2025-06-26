package ch.axa.punchclock;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ch.axa.punchclock.models.Category;
import ch.axa.punchclock.models.Entry;
import ch.axa.punchclock.models.Tags;
import ch.axa.punchclock.repositories.CategoryRepository;
import ch.axa.punchclock.repositories.EntryRepository;
import ch.axa.punchclock.repositories.TagRepository;

@Component
public class Dataloader implements ApplicationRunner {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private EntryRepository entryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createCategories();
        createTags();
        createEntries();
    }

    public void createCategories() {
        var category1 = new Category();
        category1.setTitle("Berufsschule");
        categoryRepository.save(category1);

        var category2 = new Category();
        category2.setTitle("Homeoffice");
        categoryRepository.save(category2);

        var category3 = new Category();
        category3.setTitle("Ferien");
        categoryRepository.save(category3);

        var category4 = new Category();
        category4.setTitle("Militär / Zivildienst");
        categoryRepository.save(category4);

        var category5 = new Category();
        category5.setTitle("Krankheit / Unfall");
        categoryRepository.save(category5);
    }

    public void createTags() {
        var tag1 = new Tags();
        tag1.setTitle("Prüfung");
        tagRepository.save(tag1);

        var tag2 = new Tags();
        tag2.setTitle("Abgabe");
        tagRepository.save(tag2);

        var tag3 = new Tags();
        tag3.setTitle("Projektarbeit");
        tagRepository.save(tag3);

        var tag4 = new Tags();
        tag4.setTitle("Bericht");
        tagRepository.save(tag4);

        var tag5 = new Tags();
        tag5.setTitle("Präsentation");
        tagRepository.save(tag5);

    }

    public void createEntries() {
        // Categories laden
        Category category1 = categoryRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Category 1 nicht gefunden"));
        Category category2 = categoryRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Category 2 nicht gefunden"));
        Category category3 = categoryRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("Category 3 nicht gefunden"));
        Category category4 = categoryRepository.findById(4L)
                .orElseThrow(() -> new RuntimeException("Category 4 nicht gefunden"));
        Category category5 = categoryRepository.findById(5L)
                .orElseThrow(() -> new RuntimeException("Category 5 nicht gefunden"));

        // Tags laden
        Tags tag1 = tagRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Tag 1 nicht gefunden"));
        Tags tag2 = tagRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Tag 2 nicht gefunden"));
        Tags tag3 = tagRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("Tag 3 nicht gefunden"));
        Tags tag4 = tagRepository.findById(4L)
                .orElseThrow(() -> new RuntimeException("Tag 4 nicht gefunden"));
        Tags tag5 = tagRepository.findById(5L)
                .orElseThrow(() -> new RuntimeException("Tag 5 nicht gefunden"));

        // Montag
        Entry entry1 = new Entry();
        entry1.setCheckIn(LocalDateTime.of(2025, 6, 23, 8, 0));
        entry1.setDuration(14400);
        entry1.setDescription("Einführung in neues Projekt und Einrichtung der Entwicklungsumgebung");
        entry1.setCategory(category1); // Beispiel: Betriebsarbeit
        entry1.setTags(Set.of(tag3, tag4));
        entryRepository.save(entry1);

        // Dienstag
        Entry entry2 = new Entry();
        entry2.setCheckIn(LocalDateTime.of(2025, 6, 24, 8, 30));
        entry2.setDuration(10800);
        entry2.setDescription("Projektarbeit: REST-API entworfen und erste Endpunkte umgesetzt");
        entry2.setCategory(category2);
        entry2.setTags(Set.of(tag3));
        entryRepository.save(entry2);

        // Mittwoch
        Entry entry3 = new Entry();
        entry3.setCheckIn(LocalDateTime.of(2025, 6, 25, 9, 0));
        entry3.setDuration(12600);
        entry3.setDescription("Bugfixing und Tests: Fehler im Authentifizierungssystem behoben");
        entry3.setCategory(category3);
        entry3.setTags(Set.of(tag1, tag3));
        entryRepository.save(entry3);

        // Donnerstag
        Entry entry4 = new Entry();
        entry4.setCheckIn(LocalDateTime.of(2025, 6, 26, 8, 45));
        entry4.setDuration(16200);
        entry4.setDescription("Teammeeting, Planung Sprint 5 und Review der letzten Woche");
        entry4.setCategory(category4);
        entry4.setTags(Set.of(tag4));
        entryRepository.save(entry4);

        // Freitag
        Entry entry5 = new Entry();
        entry5.setCheckIn(LocalDateTime.of(2025, 6, 27, 8, 15));
        entry5.setDuration(14400);
        entry5.setDescription("Abschlussbericht geschrieben und Projektstatus im Wiki aktualisiert");
        entry5.setCategory(category5);
        entry5.setTags(Set.of(tag2, tag5));
        entryRepository.save(entry5);
    }

}
