**Elasticsearch-java-autocomplete**

_Accoding to wikipedia_

Autocomplete, or word completion, is a feature in which an application predicts the rest of a word a user is typing. In graphical user interfaces, users can typically press the tab key to accept a suggestion or the down arrow key to accept one of several.

Elasticsearch is a search engine based on Lucene.We will be using different
approaches based on the features provided by search Engine.

First lets have a look on algorithm used by elasticsearch to learn how test 
is handled and processed by elasticsearch.

**Inverted index**

Elasticsearch employs Lucene's index structure called the "inverted index" for its full-text searches. It is a very versatile, easy to use and agile structure which provides fast and efficient text search capabilities to Elasticsearch.

An inverted index consists of:

1. A list of all the unique words, called terms, that appear in any document

2. A list of the documents in which the words appear

3. A term frequency list, which shows how many times a word has occurred



In order to get a good grasp of how an inverted index is populated, we will consider two documents to be indexed with the following contents in it.

Document 1: "elasticsearch is cool"

Document 2: "Elasticsearch is great"

Now to create the inverted index for the above two documents, we split the contents of each document into separate words. After then we create the lists of unique words and the document ids in which they occur and also the word frequency list. So our inverted index generated for the above two documents would like below. Here we generally refer the unique words occurring in the documents as "terms".

Terms|Document|Position|Term Frequency
-----|--------|--------|--------------
elasticsearch|Document-1|1|1
Elasticsearch|Document-2|1|1
is|Document-1,Document-2|2,2|2
cool|Document-1|3|1
great|Document-2|3|1

In the above inverted index table, we can see there are 4 terms, and the documents in which they occur and the frequency of the terms occurring listed.

Let's see how a simple basic search operation works. Suppose we need to search for the term "great", so when we fire the query, Elasticsearch will look into this inverted index table and will find that the required data, which is the query term (in this case "great"), occurs in document 1 and it will then show us that document.

We will be using following approaches:

* Prefix Query
* Edge Ngram
* Completion Suggester

**Prefix Query**
https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-prefix-query.html

**Edge Ngram**
https://www.elastic.co/guide/en/elasticsearch/reference/current/analysis-edgengram-tokenizer.html

**Completion Suggester**
https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters-completion.html


Prerequisites

* Install elasticsearch;
* Use mapping in .https://gist.github.com/taranjeet/aeffcf1525f6285c5449057b2092ccd6
* Insert data using bulk query. https://gist.github.com/sandeep-rajbhandari/80c3877644109c418a82f5d8f9711977
* use command mvn spring-boot:run
* use http://localhost:8080/autocomplete/prefixsearch/{text to search} for prefixsearch
* use http://localhost:8080/autocomplete/ngramsearch/{text to search} for ngram search
* use http://localhost:8080/autocomplete/suggestion/{text to search} for suggestion search


_Refrences_


https://hackernoon.com/elasticsearch-building-autocomplete-functionality-494fcf81a7cf

https://qbox.io/blog/introduction-to-elasticsearch-analyzers

https://www.elastic.co
