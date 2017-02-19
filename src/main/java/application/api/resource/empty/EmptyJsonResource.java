package application.api.resource.empty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * A class used to represent empty JSON objects.
 * Used when an empty API call response is not acceptable.
 */
@JsonSerialize
public class EmptyJsonResource {

}
