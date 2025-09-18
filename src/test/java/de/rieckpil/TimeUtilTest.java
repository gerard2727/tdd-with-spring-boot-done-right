package de.rieckpil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeUtilTest {

  @Mock TimeProvider timeProvider;
  @InjectMocks private TimeUtil unit;

  @Test
  void shouldThrowExceptionWhenDateIsInFuture() throws Exception{
    LocalDate creationDateInFuture = LocalDate.now().plusDays(1);

    when(timeProvider.getCurrentDate())
      .thenReturn(LocalDate.of(2020, 12, 24));

    assertThrows(IllegalArgumentException.class, () ->{
      unit.getDiffBetweenCreationDate(creationDateInFuture);
    });
  }

  @Test
  void shouldReturnTodayWhenCommentWasCreatedToday() throws Exception{
    LocalDate today = LocalDate.now();

    when(timeProvider.getCurrentDate())
      .thenReturn(LocalDate.now());
    String result = unit.getDiffBetweenCreationDate(today);
    assertEquals("today", result);
  }

  @Test
  void shouldReturnMoreThanAYearWhenCommentWasCreatedMoreThanAYearAgo() throws Exception{

    LocalDate oneYearAgo = LocalDate.now().minusYears(1);

    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.now());
    String result = unit.getDiffBetweenCreationDate(oneYearAgo);

    assertEquals("more than a year ago", result);
  }

  @Test
  void shouldReturnOneMonthAgoWhenCommentWasCreatedLastMonth() throws Exception{

    when(timeProvider.getCurrentDate())
      .thenReturn(LocalDate.now());
    LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

    String result = unit.getDiffBetweenCreationDate(oneMonthAgo);
    assertEquals("one month ago", result);
  }

  @Test
  void shouldReturnPluralOfMonthsWhenCommentWasCreatedMoreThanOneMonthAgo() throws Exception{

    when(timeProvider.getCurrentDate())
      .thenReturn(LocalDate.now());
    LocalDate oneMonthAgo = LocalDate.now().minusDays(70);

    String result = unit.getDiffBetweenCreationDate(oneMonthAgo);
    assertEquals("2 months ago", result);
  }

  @Test
  void shouldReturnOneDayAgoWhenCommentWasMadeYesterday() throws Exception{
    when(timeProvider.getCurrentDate())
      .thenReturn(LocalDate.now());
    LocalDate yesterday = LocalDate.now().minusDays(1);

    String result = unit.getDiffBetweenCreationDate(yesterday);
    assertEquals("one day ago", result);
  }

  @Test
  void shouldReturnPluralOfDaysWhenCommentWasMadeMoreThanOneDayAgo() throws Exception{
    when(timeProvider.getCurrentDate())
      .thenReturn(LocalDate.now());
    LocalDate twentyDaysAgo = LocalDate.now().minusDays(20);

    String result = unit.getDiffBetweenCreationDate(twentyDaysAgo);
    assertEquals("20 days ago", result);
  }


}
