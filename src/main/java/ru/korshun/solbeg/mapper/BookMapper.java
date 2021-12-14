package ru.korshun.solbeg.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.korshun.solbeg.dto.AuthorDto;
import ru.korshun.solbeg.dto.BookDto;
import ru.korshun.solbeg.dto.TagDto;
import ru.korshun.solbeg.entity.AuthorEntity;
import ru.korshun.solbeg.entity.BookEntity;
import ru.korshun.solbeg.entity.TagEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class BookMapper {
  public BookDto toDto(BookEntity entity) {
    if ( entity == null ) {
      return null;
    }

    BookDto bookDto = new BookDto();
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    Date createdAt = new Date(entity.getCreatedAt());

    if (entity.getUpdatedAt() != null) {
      Date updatedAt = new Date();
      bookDto.setUpdatedAt( format.format(updatedAt) );
    }

    bookDto.setId( entity.getId() );
    bookDto.setTitle( entity.getTitle() );
    bookDto.setPrice( entity.getPrice() );
    bookDto.setImageUrl( entity.getImageUrl() );
    bookDto.setCreatedAt( format.format(createdAt) );
    bookDto.setTag( tagEntityToTagDto( entity.getTag() ) );
    bookDto.setAuthor( authorEntityToAuthorDto( entity.getAuthor() ) );

    return bookDto;
  }

  public abstract BookEntity toEntity(BookDto dto);
  public abstract TagDto tagEntityToTagDto(TagEntity entity);
  public abstract TagEntity tagDtoTotagEntity(TagDto entity);
  public abstract AuthorDto authorEntityToAuthorDto(AuthorEntity dto);
  public abstract AuthorEntity authorDtoToauthorEntity(AuthorDto dto);
}
