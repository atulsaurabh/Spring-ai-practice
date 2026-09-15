package tech.creative.engineering.chatclient.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VectorDbService
{
    private VectorStore vectorStore;

    public VectorDbService(VectorStore vectorStore)
    {
        this.vectorStore = vectorStore;
    }

    public void loadCSV(Resource resource)
    {
        try(Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            List<Document> objects = new ArrayList<>();
            CSVParser parser = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true).get()
                    .parse(reader);
            for (CSVRecord record : parser)
            {
                String index = record.get("index");
                String budget = record.get("budget");
                String  genres = record.get("genres");
                String homepage= record.get("homepage");
                String id = record.get("id");
                String keywords = record.get("keywords");
                String original_language = record.get("original_language");
                String original_title = record.get("original_title");
                String overview = record.get("overview");
                String popularity = record.get("popularity");
                String production_companies = record.get("production_companies");
                String production_countries = record.get("production_countries");
                String release_date = record.get("release_date");
                String revenue=record.get("revenue");
                String runtime=record.get("runtime");
                String spoken_languages=record.get("spoken_languages");
                String status=record.get("status");
                String tagline=record.get("tagline");
                String title=record.get("title");
                String vote_average=record.get("vote_average");
                String vote_count=record.get("vote_count");
                String cast=record.get("cast");
                String director=record.get("director");

                String content = """
                        Index : %s
                        Budget : %s
                        Genres: %s
                        Homepage: %s
                        Keyword : %s
                        Release Date : %s
                        Original Language : %s
                        Original Title : %s
                        Description: %s
                        Popularity : %s
                        Production Companies : %s
                        Production Countries : %s
                        Cast : %s
                        Director : %s
                        Revenue : %s
                        Runtime : %s
                        Spoken Languages : %s
                        Status : %s
                        Tagline : %s
                        Title : %s
                        Vote Average : %s
                        Vote Count : %s
                        """.formatted(index,
                        budget,
                        genres,
                        homepage,
                        keywords,
                        release_date,
                        original_language,
                        original_title,
                        overview,
                        popularity,
                        production_companies,
                        production_countries,
                        cast,director,revenue,runtime,spoken_languages,status,tagline,title,vote_average,vote_count);
                Document document = new Document(content);
                document.getMetadata().put("index", index);
                document.getMetadata().put("budget", budget);
                document.getMetadata().put("genres", genres);
                document.getMetadata().put("homepage",homepage);
                document.getMetadata().put("id",id);
                document.getMetadata().put("keywords",keywords);
                document.getMetadata().put("release date",release_date);
                document.getMetadata().put("original language",original_language);
                document.getMetadata().put("original title",original_title);
                document.getMetadata().put("overview",overview);
                document.getMetadata().put("popularity",popularity);
                document.getMetadata().put("production companies",production_companies);
                document.getMetadata().put("cast",cast);
                document.getMetadata().put("director",director);
                document.getMetadata().put("spoken languages",spoken_languages);
                document.getMetadata().put("title",title);
                document.getMetadata().put("tagline",tagline);
                objects.add(document);
            }

            TokenTextSplitter tokenTextSplitter = TokenTextSplitter.builder().build();
            List<Document> splitDocuments = tokenTextSplitter.apply(objects);
            int batchSize = 100;
            for (int i = 0; i < splitDocuments.size(); i += batchSize) {
                List<Document> batch = splitDocuments.subList(i, Math.min(i + batchSize, splitDocuments.size()));
                log.info("Ingesting batch {} to {} into VectorStore...", i, Math.min(i + batchSize, splitDocuments.size()));
                vectorStore.add(batch);
            }
            log.info("Successfully loaded {} documents into VectorStore.", splitDocuments.size());
        }
        catch (Exception e)
        {
            log.error("Failed to load documents into VectorStore: {}", e.getMessage(), e);
            throw new RuntimeException("Vector database ingestion error: " + e.getMessage(), e);
        }
    }

    public List<Document> getDocuments(String question)
    {
        return vectorStore.similaritySearch(SearchRequest.builder()
                        .topK(4)
                .query(question).build());
    }

}
