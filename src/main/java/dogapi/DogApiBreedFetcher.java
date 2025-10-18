package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */
public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();
    private static final String LIST = "list";
    private static final String STATUS = "status";
    private static final String SUCCESS = "success";
    private static final String MESSAGE = "message";
    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     */
    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        // TODO Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.
        // return statement included so that the starter code can compile and run.
        final Request request = new Request.Builder()
                .url(String.format("https://dog.ceo/api/breed/%s/%s", breed, LIST))
                .build();


        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getString(STATUS).equals(SUCCESS) ) {
                final JSONArray dogs = responseBody.getJSONArray(MESSAGE);
                List<String> subBreeds = new ArrayList<>();
                for (int i = 0; i < dogs.length(); i++) {
                    subBreeds.add(String.valueOf(dogs.getString(i)));
                }
                return subBreeds;
            }
            else {
                throw new BreedNotFoundException(breed);
            }
        }
        catch (BreedNotFoundException event) {
           
            throw event;
        }
        catch (IOException e) {
            throw new BreedNotFoundException(breed);
        }

    }
}

