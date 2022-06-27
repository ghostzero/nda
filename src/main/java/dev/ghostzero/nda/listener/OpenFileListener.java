package dev.ghostzero.nda.listener;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

public class OpenFileListener implements FileEditorManagerListener {
    private static final Logger log = LoggerFactory.getLogger(OpenFileListener.class);

    @Override
    public void fileOpened(@NotNull FileEditorManager source, VirtualFile file) {
        if (file.getPath().endsWith(".nda.json")) {
            return; // always allow .nda files
        }

        File ndaFile = new File(source.getProject().getBasePath(), ".nda.json");

        if (!ndaFile.exists()) {
            return;
        }

        String relativePath = file.getPath()
                .substring(Objects.requireNonNull(source.getProject().getBasePath()).length());

        try {
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(new FileReader(ndaFile));

            if (jsonObject.has("enabled") && !jsonObject.get("enabled").getAsBoolean()) {
                return; // disabled manually by .nda.json
            }

            if (jsonObject.has("disallow")) {
                JsonArray array = jsonObject.getAsJsonArray("disallow");
                for (JsonElement element : array) {
                    if (element.getAsString().equals(relativePath)) {
                        notify(String.format("The file %s is disallowed to open.", relativePath));
                        source.closeFile(file);

                        return;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            notify(e.getLocalizedMessage());
        }
    }

    private void notify(String message) {
        Notifications.Bus.notify(new Notification("NDA", "Blocked by NDA", message, NotificationType.INFORMATION));
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, VirtualFile file) {
        // not implemented
    }
}
