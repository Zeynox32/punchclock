package ch.axa.punchclock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.axa.punchclock.models.Entry;
import ch.axa.punchclock.repositories.CategoryRepository;
import ch.axa.punchclock.repositories.EntryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/entries")
@Tag(name = "Entry", description = "Verwaltet Zeiteinträge")
public class APIEntryController {

  @Autowired
  private EntryRepository entryRepository;
  @Autowired
  private CategoryRepository categoryRepository;

  @Operation(summary = "Erstellt einen neuen Zeiteintrag", description = "Speichert einen neuen Eintrag mit gültiger Kategorie.")
  @PostMapping
  @Transactional
  @ResponseStatus(HttpStatus.CREATED)
  public Entry create(@RequestBody @Valid Entry entry) {
    var category = categoryRepository.findById(entry.getCategory().getId()).get();
    entry.setCategory(category);
    return entryRepository.save(entry);
  }

  @Operation(summary = "Listet alle Einträge auf", description = "Optionales Filtern nach Kategorie, Tag, Beschreibung oder Wort.")
  @GetMapping
  public Iterable<Entry> index(
    @Parameter(description = "Kategorie-ID zum Filtern") @RequestParam(required = false) Long categoryId,
    @Parameter(description = "Tag-ID zum Filtern") @RequestParam(required = false) Long tagId,
    @Parameter(description = "Beschreibung für Textsuche") @RequestParam(required = false) String description,
    @Parameter(description = "Einzelnes Wort für Volltextsuche") @RequestParam(required = false) String word) {
    
    if (categoryId != null) return entryRepository.findByCategory(categoryId);
    if (tagId != null) return entryRepository.findByTagsId(tagId);
    if (description != null) return entryRepository.searchDescription(description);
    if (word != null) return entryRepository.searchDescriptionByWord(word);
    return entryRepository.findAll();
  }

  @Operation(summary = "Gibt einen bestimmten Eintrag anhand der ID zurück")
  @GetMapping("/{id}")
  public ResponseEntity<Entry> read(
    @Parameter(description = "ID des Eintrags", required = true)
    @PathVariable long id) {
    return ResponseEntity.of(entryRepository.findById(id));
  }

  @Operation(summary = "Aktualisiert einen bestehenden Eintrag")
  @PutMapping("/{id}")
  public Entry update(
    @Parameter(description = "ID des Eintrags", required = true)
    @PathVariable long id,
    @RequestBody @Valid Entry entry) {
    entry.setId(id);
    return entryRepository.save(entry);
  }

  @Operation(summary = "Löscht einen Eintrag anhand der ID")
  @DeleteMapping("/{id}")
  public ResponseEntity<Entry> delete(
    @Parameter(description = "ID des zu löschenden Eintrags", required = true)
    @PathVariable long id) {
    var entry = entryRepository.findById(id);
    if (entry.isPresent()) {
      entryRepository.delete(entry.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}