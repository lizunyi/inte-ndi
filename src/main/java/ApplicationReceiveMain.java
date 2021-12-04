import com.sun.jna.ptr.IntByReference;
import com.weaver.inte.jna.common.data.AudioFrameV2Data;
import com.weaver.inte.jna.common.data.AudioFrameV3Data;
import com.weaver.inte.jna.common.data.MetadataFrameData;
import com.weaver.inte.jna.common.data.NdiSourceData;
import com.weaver.inte.jna.common.data.VideoFrameV2Data;
import com.weaver.inte.jna.find.FindData;
import com.weaver.inte.jna.find.FindLib;
import com.weaver.inte.jna.receive.ReceiveData;
import com.weaver.inte.jna.receive.ReveiveLib;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.List;


public class ApplicationReceiveMain {

    public static void main(String[] args) throws IOException {
        FindData findData = new FindData(true, "HH", "");
//        FindData findData = new FindData(true, "", "");
        try (FindLib findLib = new FindLib(findData)) {
            //查找NDI源
            List<NdiSourceData> sourceDataList = findLib.findCurrentSources(5000);
            //调用接收
            if (CollectionUtils.isEmpty(sourceDataList)) {
                return;
            }
            for (NdiSourceData sourceData : sourceDataList) {
                if (sourceData.p_ndi_name.contains("Integrated Webcam")) {
//                    continue;
                }
                final NdiSourceData source = sourceData;
                new Thread(() -> {
                    ReceiveData receiveData = new ReceiveData(source, new IntByReference(1), new IntByReference(100), true, "MyNdi");
                    try (ReveiveLib reveiveLib = new ReveiveLib(receiveData)) {
                        reveiveLib.connect(source);
                        System.out.println("receive ndi_ssource:");
                        System.out.println(source);
                        VideoFrameV2Data p_video_data = new VideoFrameV2Data();
                        AudioFrameV2Data p_audio_data2 = new AudioFrameV2Data();
                        MetadataFrameData p_metadata = new MetadataFrameData();
                        long startTills = System.currentTimeMillis();
                        do {
                            try {
                                int receiveFrameType = reveiveLib.recvCaptureV2(p_video_data, p_audio_data2, p_metadata, 5000);
                                switch (receiveFrameType) {
                                    case 0:
                                        //none
                                        System.out.println("none");
                                        break;
                                    case 1:
                                        //video
                                        System.out.println("video:------------------------------");
                                        System.out.println(p_video_data.p_data);
                                        reveiveLib.freeVideoV2(p_video_data);
                                        break;
                                    case 2:
                                        //audio
                                        System.out.println("audio:------------------------------");
                                        System.out.println(p_audio_data2.p_data);
                                        reveiveLib.freeAudioV2(p_audio_data2);
                                        break;
                                    case 3:
                                        //meta data
                                        System.out.println("meta:------------------------------");
                                        System.out.println(p_metadata.p_data);
//                                        reveiveLib.freeMetadata(p_metadata);
                                        break;
                                    case 4:
                                        System.out.println("error");
                                        break;
                                }
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } while (true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
}
