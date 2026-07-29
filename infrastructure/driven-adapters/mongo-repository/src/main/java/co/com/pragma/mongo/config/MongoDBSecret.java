package co.com.pragma.mongo.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MongoDBSecret {
    private String uri;
}
