package com.kooloco.ui;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.MediaBox;
import com.coremedia.iso.boxes.MediaHeaderBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.SampleSizeBox;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.TrackHeaderBox;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.CroppedTrack;
import com.googlecode.mp4parser.util.Matrix;
import com.googlecode.mp4parser.util.Path;
import com.kooloco.R;
import com.kooloco.scalablevideoview.ScalableVideoView;
import com.kooloco.trimmer.AndroidUtilities;
import com.kooloco.trimmer.VideoSeekBarView;
import com.kooloco.trimmer.VideoTimelineView;
import com.kooloco.util.FileUtils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class TrimmerActivity extends AppCompatActivity {


    public static final String EXTRA_VIDEO_PATH = "EXTRA_VIDEO_PATH";
    public static final String VIDEO_URI = "video_uri";
    ScalableVideoView videoView;

    public static final String VIDEO_LENGTH = "VIDEO_LENGTH";
    VideoTimelineView videoTimeLineView;
    VideoSeekBarView videoSeekBarView;
    AppCompatTextView textViewVideoDetail;
    AppCompatImageView playButton;
    int currentPlaying = 0;
    int start = 0;
    int end = 0;
    /* final String path = "/storage/emulated/0/bstar/12_223.mp4";*/
    String path = "/storage/sdcard0/jincky/12_223.mp4";
    String destPath = "/storage/sdcard0/jincky/trim.mp4";
    //MediaPlayer videoView;
    private int maxDuration = 15000;
    private AsyncTask currentTask;
    private MenuItem hdMenu;
    private boolean needCompressVideo;
    private int rotationValue;
    private int originalWidth;
    private int originalHeight;
    private int resultWidth;
    private int resultHeight;
    private int bitrate;
    private int originalBitrate;
    private float videoDuration;
    private long startTime;
    private long endTime;
    private long audioFramesSize;
    private long videoFramesSize;
    private int estimatedSize;
    private long esimatedDuration;
    private long originalSize;
    private ProgressDialog mProgressDialog;

    public static File startTrim(File src, File dst, int startMs, int endMs) throws IOException {
        FileDataSourceImpl file = new FileDataSourceImpl(src);
        com.googlecode.mp4parser.authoring.Movie movie = MovieCreator.build(file);
        // remove all tracks we will create new tracks from the old
        List<Track> tracks = movie.getTracks();
        movie.setTracks(new LinkedList<Track>());
        double startTime = startMs / 1000;
        double endTime = endMs / 1000;
        boolean timeCorrected = false;
        // Here we try to find a track that has sync samples. Since we can only start decoding
        // at such a sample we SHOULD make sure that the start of the new fragment is exactly
        // such a frame
        /*for (Track track : tracks) {
            if (track.getSyncSamples() != null && track.getSyncSamples().length > 0) {
                if (timeCorrected) {
                    // This exception here could be a false positive in case we have multiple tracks
                    // with sync samples at exactly the same positions. E.g. a single movie containing
                    // multiple qualities of the same video (Microsoft Smooth Streaming file)
                    throw new RuntimeException("The startTime has already been corrected by another track with SyncSample. Not Supported.");
                }
                startTime = correctTimeToSyncSample(track, startTime, false);
                endTime = correctTimeToSyncSample(track, endTime, true);
                timeCorrected = true;
            }
        }*/
        for (Track track : tracks) {
            long currentSample = 0;
            double currentTime = 0;
            long startSample = -1;
            long endSample = -1;

            for (int i = 0; i < track.getSampleDurations().length; i++) {
                if (currentTime <= startTime) {

                    // current sample is still before the new starttime
                    startSample = currentSample;
                }
                if (currentTime <= endTime) {
                    // current sample is after the new start time and still before the new endtime
                    endSample = currentSample;
                } else {
                    // current sample is after the end of the cropped video
                    break;
                }
                currentTime += (double) track.getSampleDurations()[i] / (double) track.getTrackMetaData().getTimescale();
                currentSample++;
            }
            movie.addTrack(new CroppedTrack(track, startSample, endSample));
        }

        Container out = new DefaultMp4Builder().build(movie);
        MovieHeaderBox mvhd = Path.getPath(out, "moov/mvhd");
        mvhd.setMatrix(Matrix.ROTATE_180);
        if (!dst.exists()) {
            dst.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(dst);
        WritableByteChannel fc = fos.getChannel();
        try {
            out.writeContainer(fc);
        } finally {
            fc.close();
            fos.close();
            file.close();
        }

        file.close();

        return dst;
    }

    private static double correctTimeToSyncSample(Track track, double cutHere, boolean next) {
        double[] timeOfSyncSamples = new double[track.getSyncSamples().length];
        long currentSample = 0;
        double currentTime = 0;
        for (int i = 0; i < track.getSampleDurations().length; i++) {
            long delta = track.getSampleDurations()[i];

            if (Arrays.binarySearch(track.getSyncSamples(), currentSample + 1) >= 0) {
                timeOfSyncSamples[Arrays.binarySearch(track.getSyncSamples(), currentSample + 1)] = currentTime;
            }
            currentTime += (double) delta / (double) track.getTrackMetaData().getTimescale();
            currentSample++;

        }
        double previous = 0;
        for (double timeOfSyncSample : timeOfSyncSamples) {
            if (timeOfSyncSample > cutHere) {
                if (next) {
                    return timeOfSyncSample;
                } else {
                    return previous;
                }
            }
            previous = timeOfSyncSample;
        }
        return timeOfSyncSamples[timeOfSyncSamples.length - 1];
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidUtilities.context = this;

        setContentView(R.layout.video_trimmer_layout);

        Intent extraIntent = getIntent();

        if (extraIntent != null) {
            path = extraIntent.getStringExtra(EXTRA_VIDEO_PATH);
            maxDuration = extraIntent.getIntExtra(VIDEO_LENGTH, 1) * 1000;
            if (path == null)
                finish();
        }

        File root = new File(Environment.getExternalStorageDirectory() + "/.kooloco/");

        if (!root.exists()) {
            root.mkdirs();
        }


        String imageName = "video_" + System.currentTimeMillis() + ".mp4";

        File sdImageMainDirectory = new File(root, imageName);

        destPath = sdImageMainDirectory.getAbsolutePath();


        Log.e(" Destination path ", " " + destPath);

        videoTimeLineView = (VideoTimelineView) findViewById(R.id.videoTimeLineView);
        videoSeekBarView = (VideoSeekBarView) findViewById(R.id.videoSeekBarView);
        textViewVideoDetail = (AppCompatTextView) findViewById(R.id.textViewVideoDetail);
        videoView = (ScalableVideoView) findViewById(R.id.videoView);

        try {

            if (path != null) {
                videoView.setDataSource(path);
                videoView.prepare();
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        findViewById(R.id.buttonCancel)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

        findViewById(R.id.buttonDone)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        playButton.setImageResource(R.drawable.video_edit_play);
                        videoView.pause();

                        int start = Math.round(videoTimeLineView.getLeftProgress() * videoTimeLineView.getVideoLength());
                        int end = Math.round(videoTimeLineView.getRightProgress() * videoTimeLineView.getVideoLength());
                        if ((end - start) < 1000) {
                            Toast.makeText(TrimmerActivity.this, "Please select more than 1 second video", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mProgressDialog = new ProgressDialog(TrimmerActivity.this);
                        mProgressDialog.setCancelable(false);
                        mProgressDialog.setMessage("Trimming your video...");
                        mProgressDialog.show();
                        Log.e("Duration ", " " + (end - start));

                        new AsyncTask<String, Void, File>() {
                            @Override
                            protected File doInBackground(String... strings) {
                                File file = null;
                                try {
                                    file = startTrim(new File(strings[0]), new File(strings[1]), parseInt(strings[2]), parseInt(strings[3]));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return file;
                            }

                            @Override
                            protected void onPostExecute(File file) {
                                super.onPostExecute(file);
                                mProgressDialog.cancel();
                                Intent intent = new Intent();
                                Uri uri = Uri.fromFile(file);
                                intent.putExtra(VIDEO_URI, uri.toString());
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                                Log.e("Saveed at ", " " + file.getAbsolutePath());
                            }
                        }.execute(path, destPath, String.valueOf(start), String.valueOf(end));

                    }
                });


        playButton = (AppCompatImageView) findViewById(R.id.buttonPlay);
        playButton
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!videoView.isPlaying()) {

                            playButton.setImageDrawable(null);

                            if (currentPlaying > start && currentPlaying < end) {
                                videoView.seekTo(currentPlaying);
                            } else videoView.seekTo(start);
                            videoView.start();

                            startPlayBack();

                        } else {
                            playButton.setImageResource(R.drawable.video_edit_play);
                            videoView.pause();
                            currentPlaying = videoView.getCurrentPosition();
                            /*if (currentTask != null && !currentTask.isCancelled()) {
                                currentTask.cancel(true);
                            }*/
                        }
                    }
                });

        videoTimeLineView.setVideoPath(path, maxDuration);
        end = (int) (videoTimeLineView.getVideoLength() * videoTimeLineView.getMax());

        videoTimeLineView.setDelegate(new VideoTimelineView.VideoTimelineViewDelegate() {
            @Override
            public void onLeftProgressChanged(float progress) {
                updateProgress(progress, videoTimeLineView.getRightProgress());
            }

            @Override
            public void onRifhtProgressChanged(float progress) {
                updateProgress(videoTimeLineView.getLeftProgress(), progress);
            }
        });
        videoSeekBarView.setDelegate(new VideoSeekBarView.SeekBarDelegate() {
            @Override
            public void onSeekBarDrag(float progress) {

                if (progress < videoTimeLineView.getLeftProgress()) {
                    progress = videoTimeLineView.getLeftProgress();
                    videoSeekBarView.setProgress(progress);
                } else if (progress > videoTimeLineView.getRightProgress()) {
                    progress = videoTimeLineView.getRightProgress();
                    videoSeekBarView.setProgress(progress);
                }

                if (progress >= videoTimeLineView.getLeftProgress() && progress <= videoTimeLineView.getRightProgress()) {
                    currentPlaying = (int) (progress * videoTimeLineView.getVideoLength());
                    videoView.seekTo(currentPlaying);
                }

            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                currentPlaying = start;
            }
        });


    }


    private synchronized void updateProgress(float left, float right) {

        stopPlaying();
        videoSeekBarView.setMin(left);
        videoSeekBarView.setMax(right);

        end = (int) (right * (float) videoTimeLineView.getVideoLength());
        currentPlaying = start = (int) (left * (float) videoTimeLineView.getVideoLength());

        videoView.seekTo(start);
        videoSeekBarView.setProgress(left);

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        hdMenu = menu.add(1, 1, 1, "HD")
                .setIcon(R.drawable.hd_on)

        ;
        hdMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        hdMenu.setCheckable(true);
        hdMenu.setChecked(true);
        processOpenVideo();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == 1) {
            hdMenu.setIcon(hdMenu.isChecked() ? R.drawable.hd_off : R.drawable.hd_on);
            hdMenu.setChecked(!hdMenu.isChecked());
            needCompressVideo = !needCompressVideo;
            updateVideoInfo();
        }

        return super.onOptionsItemSelected(item);
    }*/

    private void stopPlaying() {
        if (videoView.isPlaying()) {
            videoView.pause();
            currentPlaying = start;
            videoView.seekTo(start);
            videoSeekBarView.setProgress(start / (float) videoTimeLineView.getVideoLength());
            playButton.setImageResource(R.drawable.video_edit_play);
        }
    }

    private void startPlayBack() {

        currentTask = new AsyncTask<Void, Float, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                while (videoView.isPlaying()) {
                    currentPlaying = videoView.getCurrentPosition();
                    if (currentPlaying >= end) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stopPlaying();
                            }
                        });

                        break;
                    }
                    float p = currentPlaying / (float) videoTimeLineView.getVideoLength();
                    publishProgress(p);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }

            @Override
            protected void onProgressUpdate(Float... values) {
                super.onProgressUpdate(values);
                // Log.e(" Progress ", " " + values[0]);
                videoSeekBarView.setProgress(values[0]);

            }
        }.execute();


    }

    private void updateVideoInfo() {

        esimatedDuration = (long) Math.ceil((videoTimeLineView.getRightProgress() - videoTimeLineView.getLeftProgress()) * videoDuration);

        int width;
        int height;

        if (!hdMenu.isVisible() || hdMenu.isVisible() && !needCompressVideo) {
            width = rotationValue == 90 || rotationValue == 270 ? originalHeight : originalWidth;
            height = rotationValue == 90 || rotationValue == 270 ? originalWidth : originalHeight;
            estimatedSize = (int) (originalSize * ((float) esimatedDuration / videoDuration));
        } else {
            width = rotationValue == 90 || rotationValue == 270 ? resultHeight : resultWidth;
            height = rotationValue == 90 || rotationValue == 270 ? resultWidth : resultHeight;

            estimatedSize = (int) ((audioFramesSize + videoFramesSize) * ((float) esimatedDuration / videoDuration));
            estimatedSize += estimatedSize / (32 * 1024) * 16;
        }

        if (videoTimeLineView.getLeftProgress() == 0) {
            startTime = -1;
        } else {
            startTime = (long) (videoTimeLineView.getLeftProgress() * videoDuration) * 1000;
        }
        if (videoTimeLineView.getRightProgress() == 1) {
            endTime = -1;
        } else {
            endTime = (long) (videoTimeLineView.getRightProgress() * videoDuration) * 1000;
        }

        String videoDimension = String.format("%dx%d", width, height);
        int minutes = (int) (esimatedDuration / 1000 / 60);
        int seconds = (int) Math.ceil(esimatedDuration / 1000) - minutes * 60;
        String videoTimeSize = String.format("%d:%02d, ~%s", minutes, seconds, AndroidUtilities.formatFileSize(estimatedSize));
        textViewVideoDetail.setText(String.format("%s, %s", videoDimension, videoTimeSize));
    }

    private boolean processOpenVideo() {
        try {
            File file = new File(path);
            originalSize = file.length();

            IsoFile isoFile = new IsoFile(path);
            List<Box> boxes = Path.getPaths(isoFile, "/moov/trak/");
            TrackHeaderBox trackHeaderBox = null;
            boolean isAvc = true;
            boolean isMp4A = true;

            Box boxTest = Path.getPath(isoFile, "/moov/trak/mdia/minf/stbl/stsd/mp4a/");
            if (boxTest == null) {
                isMp4A = false;
            }

            if (!isMp4A) {
                return false;
            }

            boxTest = Path.getPath(isoFile, "/moov/trak/mdia/minf/stbl/stsd/avc1/");
            if (boxTest == null) {
                isAvc = false;
            }

            for (Box box : boxes) {
                TrackBox trackBox = (TrackBox) box;
                long sampleSizes = 0;
                long trackBitrate = 0;
                try {
                    MediaBox mediaBox = trackBox.getMediaBox();
                    MediaHeaderBox mediaHeaderBox = mediaBox.getMediaHeaderBox();
                    SampleSizeBox sampleSizeBox = mediaBox.getMediaInformationBox().getSampleTableBox().getSampleSizeBox();
                    for (long size : sampleSizeBox.getSampleSizes()) {
                        sampleSizes += size;
                    }
                    videoDuration = (float) mediaHeaderBox.getDuration() / (float) mediaHeaderBox.getTimescale();
                    trackBitrate = (int) (sampleSizes * 8 / videoDuration);
                } catch (Exception e) {

                }
                TrackHeaderBox headerBox = trackBox.getTrackHeaderBox();
                if (headerBox.getWidth() != 0 && headerBox.getHeight() != 0) {
                    trackHeaderBox = headerBox;
                    originalBitrate = bitrate = (int) (trackBitrate / 100000 * 100000);
                    if (bitrate > 900000) {
                        bitrate = 900000;
                    }
                    videoFramesSize += sampleSizes;
                } else {
                    audioFramesSize += sampleSizes;
                }
            }
            if (trackHeaderBox == null) {
                return false;
            }

            Matrix matrix = trackHeaderBox.getMatrix();
            if (matrix.equals(Matrix.ROTATE_90)) {
                rotationValue = 90;
            } else if (matrix.equals(Matrix.ROTATE_180)) {
                rotationValue = 180;
            } else if (matrix.equals(Matrix.ROTATE_270)) {
                rotationValue = 270;
            }
            resultWidth = originalWidth = (int) trackHeaderBox.getWidth();
            resultHeight = originalHeight = (int) trackHeaderBox.getHeight();

            if (resultWidth > 640 || resultHeight > 640) {
                float scale = resultWidth > resultHeight ? 640.0f / resultWidth : 640.0f / resultHeight;
                resultWidth *= scale;
                resultHeight *= scale;
                if (bitrate != 0) {
                    bitrate *= Math.max(0.5f, scale);
                    videoFramesSize = (long) (bitrate / 8 * videoDuration);
                }
            }

            if (!isAvc && (resultWidth == originalWidth || resultHeight == originalHeight)) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }

        videoDuration *= 1000;
        updateVideoInfo();
        return true;
    }
}