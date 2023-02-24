package ru.nsu.fit.makhov.pizzeria.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import ru.nsu.fit.makhov.pizzeria.workers.BakeryWorker;
import ru.nsu.fit.makhov.pizzeria.workers.DeliveryWorker;

/**
 * Reader of jsons.
 */
public final class JsonReader {

  private JsonReader() {
    throw new UnsupportedOperationException();
  }

  /**
   * Get bakers from json.
   *
   * @param jsonName json name.
   * @return optional List of bakers.
   */
  public static Optional<List<BakeryWorker>> getBakeryWorkers(String jsonName) {

    Gson gson = new Gson();
    Optional<List<BakeryWorker>> bakeryWorkers = Optional.empty();
    try (Reader reader = new FileReader(jsonName)) {
      bakeryWorkers = Optional.ofNullable(
          gson.fromJson(reader, new TypeToken<List<BakeryWorker>>() {
          }.getType()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bakeryWorkers;
  }

  /**
   * Get drivers from json.
   *
   * @param jsonName json name.
   * @return optional List of drivers.
   */
  public static Optional<List<DeliveryWorker>> getDeliveryWorkers(String jsonName) {
    Gson gson = new Gson();
    Optional<List<DeliveryWorker>> deliveryWorkers = Optional.empty();
    try (Reader reader = new FileReader(jsonName)) {
      deliveryWorkers = Optional.ofNullable(
          gson.fromJson(reader, new TypeToken<List<DeliveryWorker>>() {
          }.getType()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return deliveryWorkers;
  }


}
