package ru.korshun.solbeg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korshun.solbeg.config.exception.DataNotFoundException;
import ru.korshun.solbeg.dto.AuthorDto;
import ru.korshun.solbeg.dto.BookDto;
import ru.korshun.solbeg.dto.TagDto;
import ru.korshun.solbeg.dto.request.EditBookRequestDto;
import ru.korshun.solbeg.entity.AuthorEntity;
import ru.korshun.solbeg.entity.BookEntity;
import ru.korshun.solbeg.entity.TagEntity;
import ru.korshun.solbeg.mapper.BookMapper;
import ru.korshun.solbeg.repository.BookRepository;
import ru.korshun.solbeg.repository.TagRepository;
import ru.korshun.solbeg.security.CustomUserDetails;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final TagRepository tagRepository;
  private final BookMapper bookMapper;

  @Transactional
  public Page<BookDto> getBooks(Pageable pageable) {

    Page<BookEntity> resultPage = bookRepository.findAll(pageable);

    if (resultPage.getTotalPages() == 0) {
      throw new DataNotFoundException("Data not found");
    }

    if (pageable.getPageNumber() >= resultPage.getTotalPages()) {
      throw new DataNotFoundException(String.format(
              "Data not found with page %d and size %d", pageable.getPageNumber(), pageable.getPageSize()));
    }
    return resultPage.map(bookMapper::toDto);
  }

  @Transactional
  public Page<BookDto> getBooksByTag(String tag, Pageable pageable) {
    TagEntity tagEntity = tagRepository.findByNameIgnoreCase(tag);

    if (tagEntity == null) {
      throw new DataNotFoundException(String.format("Wrong tag \"%s\"", tag));
    }

    Page<BookEntity> resultPage = bookRepository.findByTag(tagEntity, pageable);

    if (resultPage.getTotalPages() == 0) {
      throw new DataNotFoundException(String.format("Data not found with tag \"%s\"", tag));
    }

    if (pageable.getPageNumber() >= resultPage.getTotalPages()) {
      throw new DataNotFoundException(String.format(
              "Data not found with page %d and size %d", pageable.getPageNumber(), pageable.getPageSize()));
    }

    return resultPage.map(bookMapper::toDto);
  }

  @Transactional
  public Page<BookDto> getBooksByTitle(String title, Pageable pageable) {

    Page<BookEntity> resultPage = bookRepository.findByTitleContainingIgnoreCase(title, pageable);

    if (resultPage.getTotalPages() == 0) {
      throw new DataNotFoundException(String.format("Data not found with title \"%s\"", title));
    }

    if (pageable.getPageNumber() >= resultPage.getTotalPages()) {
      throw new DataNotFoundException(String.format(
              "Data not found with page %d and size %d", pageable.getPageNumber(), pageable.getPageSize()));
    }

    return resultPage.map(bookMapper::toDto);
  }

  @Transactional
  public Page<BookDto> getBooksByTitleAndTag(String title, String tag, Pageable pageable) {
    TagEntity tagEntity = tagRepository.findByNameIgnoreCase(tag);
    Page<BookEntity> resultPage;

    if (tagEntity == null) {
      resultPage = bookRepository.findByTitleContainingIgnoreCase(title, pageable);
    } else {
      resultPage = bookRepository.findByTitleContainingIgnoreCaseOrTag(title, tagEntity, pageable);
    }

    if (resultPage.getTotalPages() == 0) {
      throw new DataNotFoundException(String.format("Data not found with title \"%s\" and tag \"%s\"", title, tag));
    }

    if (pageable.getPageNumber() >= resultPage.getTotalPages()) {
      throw new DataNotFoundException(String.format(
              "Data not found with page %d and size %d", pageable.getPageNumber(), pageable.getPageSize()));
    }

    return resultPage.map(bookMapper::toDto);
  }

  @Transactional
  @PreAuthorize(
          "hasAuthority('" + CustomUserDetails.PRIVILEGE_WRITE + "') or " +
          "hasAuthority('" + CustomUserDetails.PRIVILEGE_EDIT + "')")
  public void edit(Long id, EditBookRequestDto bookRequestDto) {

    BookEntity bookEntity = bookRepository.findById(id).orElseThrow(() ->
            new DataNotFoundException("Book not found"));

    AuthorEntity authorEntity = bookMapper.authorDtoToauthorEntity(new AuthorDto(
       bookRequestDto.getAuthorFirstName(),
       bookRequestDto.getAuthorLastName()
    ));
    TagEntity tagEntity = bookMapper.tagDtoTotagEntity(new TagDto(bookRequestDto.getTag()));

    bookEntity.setAuthor(authorEntity);
    bookEntity.setTag(tagEntity);
    bookEntity.setTitle(bookRequestDto.getTitle());
    bookEntity.setImageUrl(bookRequestDto.getImageUrl());
    bookEntity.setPrice(bookRequestDto.getPrice());
    bookEntity.setUpdatedAt(System.currentTimeMillis());

    bookRepository.save(bookEntity);

  }


  @Transactional
  @PreAuthorize("hasAuthority('" + CustomUserDetails.PRIVILEGE_WRITE + "')")
  public void addBook(EditBookRequestDto bookRequestDto) {

    BookDto bookDto = new BookDto();
    AuthorDto authorDto = new AuthorDto(
            bookRequestDto.getAuthorFirstName(),
            bookRequestDto.getAuthorLastName()
    );
    TagDto tagDto = new TagDto(bookRequestDto.getTag());

    bookDto.setTitle(bookRequestDto.getTitle());
    bookDto.setPrice(bookRequestDto.getPrice());
    bookDto.setImageUrl(bookRequestDto.getImageUrl());
    bookDto.setAuthor(authorDto);
    bookDto.setTag(tagDto);

    BookEntity bookEntity = bookMapper.toEntity(bookDto);
    bookEntity.setCreatedAt(System.currentTimeMillis());

    bookRepository.save(bookEntity);

  }


  @Transactional
  @PreAuthorize("hasAuthority('" + CustomUserDetails.PRIVILEGE_DELETE + "')")
  public void delBook(long id) {

    BookEntity bookEntity = bookRepository.findById(id).orElseThrow(() ->
            new DataNotFoundException("Book not found"));

    bookRepository.delete(bookEntity);
  }
}
