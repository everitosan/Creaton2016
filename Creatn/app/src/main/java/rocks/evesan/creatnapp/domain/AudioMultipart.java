package rocks.evesan.creatnapp.domain;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rocks.evesan.creatnapp.Constants.RecordConstants;

/**
 * Created by evesan on 8/26/16.
 */
public class AudioMultipart {

    private MultipartBody.Builder audio_body;
    private  MultipartBody.Part audio;
    private  MultipartBody.Part audio_name;
    private  MultipartBody.Part audio_lat;
    private  MultipartBody.Part audio_lon;
    private  MultipartBody.Part audio_tag;

    public AudioMultipart(String[] args) {
        // args => [file_name, name, lat, lon, tag]
        File file = new File( args[0] );
        RequestBody fbody =  RequestBody.create(MediaType.parse("video/3gpp"), file);

        audio = MultipartBody.Part
                .createFormData("history[audio]", file.getName(), fbody);
        audio_name = MultipartBody.Part.createFormData("history[name]", args[1]);
        audio_lat = MultipartBody.Part.createFormData("history[latitude]", args[2]);
        audio_lon = MultipartBody.Part.createFormData("history[longitude]", args[3]);
        audio_tag = MultipartBody.Part.createFormData("history[tag]", args[4]);

        audio_body = new MultipartBody.Builder()
                .setType( MultipartBody.FORM)
                .addPart(audio)
                .addPart(audio_name)
                .addPart(audio_lat)
                .addPart(audio_lon)
                .addPart(audio_tag);
    }

    public MultipartBody getMultiBody() {
        return audio_body.build();
    }

    public MultipartBody.Part getName() {
        return audio_name;
    }

    public MultipartBody.Part getAudio_lat() {
        return audio_lat;
    }

    public MultipartBody.Part getAudio_lon() {
        return audio_lon;
    }

    public MultipartBody.Part getAudio_tag() {
        return audio_tag;
    }

    public MultipartBody.Part getAudio() {
        return audio;
    }
}
