//@Modifying
//@Query("update User u set u.firstname = ?1 where u.lastname = ?2")
//int setFixedFirstnameFor(String firstname, String lastname);


//@Transactional
//@Modifying
//@Query(value = "UPDATE az_news a SET a.news_content =:#\{#news.newsContent\} WHERE news_id =:#\{#news.newsId\}", nativeQuery = true)
//void updateNews(News news);