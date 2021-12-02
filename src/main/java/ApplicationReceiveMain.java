import com.sun.jna.ptr.IntByReference;
import com.weaver.inte.jna.common.data.AudioFrameV2Data;
import com.weaver.inte.jna.common.data.AudioFrameV3Data;
import com.weaver.inte.jna.common.data.MetadataFrameData;
import com.weaver.inte.jna.common.data.NdiSourceData;
import com.weaver.inte.jna.common.data.VideoFrameV2Data;
import com.weaver.inte.jna.find.FindData;
import com.weaver.inte.jna.find.FindLib;
import com.weaver.inte.jna.frame.FrameLib;
import com.weaver.inte.jna.receive.ReceiveData;
import com.weaver.inte.jna.receive.ReveiveLib;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.List;


public class ApplicationReceiveMain {

    public static void main(String[] args) throws IOException {
        FindData findData = new FindData(true, "Memo", "192.168.1.128");
        try (FindLib findLib = new FindLib(findData)) {
            //查找NDI源
            List<NdiSourceData> sourceDataList = findLib.findCurrentSources(1000);
            //调用接收
            if (CollectionUtils.isEmpty(sourceDataList)) {
                return;
            }
            for (NdiSourceData sourceData : sourceDataList) {
                if (!sourceData.p_ndi_name.contains("Integrated Webcam")) {
                    continue;
                }
                ReceiveData receiveData = new ReceiveData(sourceData, new IntByReference(1), new IntByReference(100), true, "MyNdi");
                try (ReveiveLib reveiveLib = new ReveiveLib(receiveData)) {
                    reveiveLib.connect(sourceData);
                    VideoFrameV2Data p_video_data = new VideoFrameV2Data();
                    AudioFrameV2Data p_audio_data2 = new AudioFrameV2Data();
                    AudioFrameV3Data p_audio_data3 = new AudioFrameV3Data();
                    MetadataFrameData p_metadata = new MetadataFrameData();
                    int receiveFrameType;
                    long startTills = System.currentTimeMillis();
                    do {
                        receiveFrameType = reveiveLib.recvCaptureV3(p_video_data, p_audio_data3, p_metadata, 5000);
                        if (receiveFrameType == 3) {
                            System.err.println("reve MetadataFrameData：" + p_metadata.p_data);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (System.currentTimeMillis() - startTills < 60000);
//                    try (FrameLib frameLib = new FrameLib(reveiveLib.insPoint)) {
//                        do {
//                            frameLib.captureAudio(p_audio_data2, 48000, 2, 0);
//                            System.out.println(p_audio_data2);
//                            Thread.sleep(1000);
//                        } while (System.currentTimeMillis() - startTills < 3000);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }
    }
}
