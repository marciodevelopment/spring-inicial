package br.org.curitiba.ici.saude.common;



import java.util.Collection;
import org.springframework.data.domain.Page;
import lombok.Data;

@Data
public class PageResponse<T> {
  private Collection<T> content;
  private int numberOfElements;
  private int size;
  private long totalElements;
  private int totalPages;

  public PageResponse(Page<?> page, Collection<T> responses) {
    this.content = responses;
    this.numberOfElements = page.getNumberOfElements();
    this.size = page.getSize();
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
  }


  @SuppressWarnings("unchecked")
  public PageResponse(Page<?> page) {
    this.content = (Collection<T>) page.getContent();
    this.numberOfElements = page.getNumberOfElements();
    this.size = page.getSize();
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
  }


  public PageResponse(Collection<T> response, Page<?> pageInformation) {
    this.content = response;
    this.numberOfElements = pageInformation.getNumberOfElements();
    this.size = pageInformation.getSize();
    this.totalElements = pageInformation.getTotalElements();
    this.totalPages = pageInformation.getTotalPages();
  }

}
