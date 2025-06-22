package cch.utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.*;

public abstract class IconLoader {

  private IconLoader() {}

  private static final Map<String, ImageIcon> iconMap = new ConcurrentHashMap<>();

  public static ImageIcon getIcon(String path) {

    if (iconMap.containsKey(path)) {
      return iconMap.get(path);
    }
    try (final var stream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
      if (stream == null) {
        System.err.println("Recurso: " + path + " não encontrado");
        return null;
      }
      final ImageIcon imageIcon = new ImageIcon(stream.readAllBytes());
      iconMap.put(path, imageIcon);
      return imageIcon;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
