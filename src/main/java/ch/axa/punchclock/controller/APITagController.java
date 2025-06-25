package ch.axa.punchclock.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.axa.punchclock.models.Tag;
import ch.axa.punchclock.repositories.TagRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tag")
public class APITagController {

  @Autowired
  private TagRepository tagRepository;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Tag create(@RequestBody @Valid Tag tag) {
    return tagRepository.save(tag);
  }

  @GetMapping
  public Iterable<Tag> index() {
    return tagRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Tag> read(@PathVariable Long id) {
    return ResponseEntity.of(tagRepository.findById(id));
  }

  @PutMapping("/{id}")
  public Tag update(@PathVariable Long id, @RequestBody @Valid Tag tag) {
    tag.setId(id);
    return tagRepository.save(tag);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Tag> delete(@PathVariable Long id) {
    var tag = tagRepository.findById(id);
    if(tag.isPresent()) {
      tagRepository.delete(tag.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
