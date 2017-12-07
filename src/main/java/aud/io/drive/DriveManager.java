package aud.io.drive;

import aud.io.log.Logger;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;

import static aud.io.drive.DriveService.getDriveService;

public class DriveManager {

    private Drive driveService;
    private Logger logger;

    public DriveManager() throws IOException
    {
        driveService =  getDriveService();
        logger = new Logger("DriveManager", Level.ALL, Level.ALL);
    }

    public String download(String searchValue) throws IOException
    {
            String fileToken = null;
            String pageToken = null;
            OutputStream outputStream = null;
            do try {
                FileList result = driveService.files().list()
                        .setQ("name='" + searchValue + "'")
                        .setSpaces("drive")
                        .setFields("nextPageToken, files(id, name)")
                        .setPageToken(pageToken)
                        .execute();
                for (File file : result.getFiles()) {
                    fileToken = file.getId();
                    break;
                }
                pageToken = result.getNextPageToken();


                //Uitvoeren van de filedownload
                String filelocation = "./Music/" + fileToken + ".mp3";
                outputStream = new FileOutputStream(new java.io.File(filelocation));
                driveService.files().get(fileToken)
                        .executeMediaAndDownloadTo(outputStream);
                logger.log(Level.INFO, "Downloaden van file gelukt");
                return filelocation;
            } catch (Exception ex) {
                logger.log(Level.WARNING, "Downloaden mislukt");
                return null;
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            } while (pageToken != null);
    }

    public boolean upload(java.io.File uploadFile, String fileName) throws IOException
    {
        FileList result = driveService.files().list()
                .setQ("name='" + fileName + "'")
                .setSpaces("drive")
                .setFields("nextPageToken, files(id, name)")
                .execute();

        if (result.getFiles().isEmpty())
        {
            //Toevoegen van titel aan de file
            File fileMetadata = new File();
            fileMetadata.setName(fileName);

            //Aanmaken van de file voor google drive
            FileContent mediaContent = new FileContent("", uploadFile);

            //Uploaden naar de drive
            Drive.Files.Create create = driveService.files().create(fileMetadata, mediaContent);
            MediaHttpUploader uploader = create.getMediaHttpUploader();
            uploader.setDirectUploadEnabled(true);
            create.execute();
            return true;
        }
        else
        {
            System.out.println("This item already exists");
            logger.log(Level.WARNING, "This item already exists");
            return false;
        }
    }
}
