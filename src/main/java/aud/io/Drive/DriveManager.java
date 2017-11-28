package aud.io.Drive;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static aud.io.Drive.DriveService.getDriveService;

public class DriveManager {

    Drive driveService;

    public DriveManager() throws IOException
    {
        driveService =  getDriveService();
    }

    public void download(String searchValue) throws IOException
    {
        String fileToken = null;
        String pageToken = null;
        do {
            FileList result = driveService.files().list()
                    .setQ("name='Test.mp3'")
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name)")
                    .setPageToken(pageToken)
                    .execute();
            for (File file : result.getFiles())
            {
                System.out.printf("Found file: %s (%s)\n",
                        file.getName(), file.getId());
                fileToken = file.getId();
            }
            pageToken = result.getNextPageToken();

            //Uitvoeren van de filedownload
            OutputStream outputStream = new FileOutputStream(new java.io.File("C:\\Users\\GieForce\\School\\Downloads\\" + fileToken + ".mp3"));
            driveService.files().get(fileToken)
                    .executeMediaAndDownloadTo(outputStream);

        }while (pageToken != null);
    }

    public boolean upload(java.io.File uploadFile, String fileName) throws IOException
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
}
