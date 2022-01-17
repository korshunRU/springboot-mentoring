package ru.korshun.solbeg.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.korshun.solbeg.dto.BookDto;
import ru.korshun.solbeg.dto.request.EditBookRequestDto;
import ru.korshun.solbeg.service.BookService;
import ru.korshun.solbeg.utils.BaseResponse;

@Slf4j
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @Operation(
          summary = "View list of books endpoint",
          description = "View list of books endpoint",
          parameters = {
          @Parameter(
                  name = "page",
                  schema = @Schema(
                          type = "integer",
                          minimum = "0",
                          defaultValue = "0"
                  ),
                  in = ParameterIn.QUERY,
                  description = "Zero based page number of the books you want to retrieve"),
          @Parameter(
                  name = "size",
                  schema = @Schema(
                          type = "integer",
                          minimum = "1",
                          defaultValue = "15"
                  ),
                  in = ParameterIn.QUERY,
                  description = "The amount of books you want to retrieve"),
          @Parameter(
                  name = "tag",
                  schema = @Schema(type = "string"),
                  in = ParameterIn.QUERY,
                  description = "Tag of the book"),
          @Parameter(
                  name = "title",
                  schema = @Schema(type = "string"),
                  in = ParameterIn.QUERY,
                  description = "Title of the book")
  })
  @ApiResponse(
          responseCode = "404",
          description = "Books not found by request parameters",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = BaseResponse.class
                  )
          )
  )
  @ApiResponse(
          responseCode = "200",
          description = "Success"
          )
  @GetMapping(
          path = "/list",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.OK)
  private ResponseEntity<BaseResponse<Page<BookDto>>> getBooks(
          @RequestParam(value = "tag", required = false) String tag,
          @RequestParam(value = "title", required = false) String title,
          @RequestParam(value = "page", defaultValue = "0") int page,
          @RequestParam(value = "size", defaultValue = "15") int size
  ) {

    Pageable pageable = PageRequest.of(page, size);
    Page<BookDto> resultPage;

    if (title != null && tag != null) {
      resultPage = bookService.getBooksByTitleAndTag(title, tag, pageable);
    } else if (title != null) {
      resultPage = bookService.getBooksByTitle(title, pageable);
    } else if (tag != null) {
      resultPage = bookService.getBooksByTag(tag, pageable);
    } else {
      resultPage = bookService.getBooks(pageable);
    }
    return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(resultPage));
  }



  @Operation(
          summary = "Edit book description",
          description = "Edit book description",
          parameters = {
                  @Parameter(
                          name = "id",
                          schema = @Schema(
                                  type = "integer",
                                  required = true
                          ),
                          in = ParameterIn.QUERY,
                          description = "Book id"),
                  @Parameter(
                          name = "bookRequestDto",
                          schema = @Schema(
                                  implementation = EditBookRequestDto.class,
                                  required = true
                          ),
                          in = ParameterIn.QUERY,
                          description = "Book fields")
          })
  @ApiResponse(
          responseCode = "401",
          description = "Trying to access endpoint without autorization",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = BaseResponse.class
                  )
          )
  )
  @ApiResponse(
          responseCode = "404",
          description = "Books not found by request parameters",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BaseResponse.class)
          )
  )
  @ApiResponse(
          responseCode = "403",
          description = "Don't have required permissions to perform this action",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BaseResponse.class)
          )
  )
  @ApiResponse(
          responseCode = "200",
          description = "Success",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BaseResponse.class))
  )
  @PutMapping(
          path = "/edit",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.OK)
  private ResponseEntity<BaseResponse<Void>> editBook(
          @RequestParam(value = "id") Long bookId,
          @RequestBody EditBookRequestDto bookRequestDto
  ) {
    bookService.edit(bookId, bookRequestDto);
    return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(HttpStatus.OK));
  }


  @Operation(
          summary = "Adding new book",
          description = "Adding new book",
          parameters = {
                  @Parameter(
                          name = "bookRequestDto",
                          schema = @Schema(
                                  implementation = EditBookRequestDto.class,
                                  required = true
                          ),
                          in = ParameterIn.QUERY,
                          description = "Book fields")
          })
  @ApiResponse(
          responseCode = "400",
          description = "Error, book not added",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = BaseResponse.class
                  )
          )
  )
  @ApiResponse(
          responseCode = "401",
          description = "Trying to access endpoint without autorization",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = BaseResponse.class
                  )
          )
  )
  @ApiResponse(
          responseCode = "403",
          description = "Don't have required permissions to perform this action",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = BaseResponse.class
                  )
          )
  )
  @ApiResponse(responseCode = "200",
          description = "Success",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = BaseResponse.class
                  )
          )
  )
  @PostMapping(
          path = "/new",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.OK)
  private ResponseEntity<BaseResponse<Void>> newBook(@RequestBody EditBookRequestDto bookRequestDto) {
    bookService.addBook(bookRequestDto);
    return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(HttpStatus.OK));
  }


  @Operation(
          summary = "Delete book by id",
          description = "Delete book by id",
          parameters = {
                  @Parameter(
                          name = "id",
                          schema = @Schema(
                                  type = "integer",
                                  required = true
                          ),
                          in = ParameterIn.QUERY,
                          description = "Book id")
          })
  @ApiResponse(
          responseCode = "401",
          description = "Trying to access endpoint without autorization",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = BaseResponse.class
                  )
          )
  )
  @ApiResponse(
          responseCode = "404",
          description = "Book with this id not found",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = BaseResponse.class
                  )
          )
  )
  @ApiResponse(
          responseCode = "403",
          description = "Don't have required permissions to perform this action",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = BaseResponse.class
                  )
          )
  )
  @ApiResponse(
          responseCode = "200",
          description = "Success",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = BaseResponse.class
                  )
          )
  )
  @DeleteMapping(
          path = "/del",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.OK)
  private ResponseEntity<BaseResponse<Void>> delBook(@RequestParam(value = "id") Long bookId) {
    bookService.delBook(bookId);
    return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(HttpStatus.OK));
  }

}
