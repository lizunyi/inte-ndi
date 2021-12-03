import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
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
import com.weaver.inte.jna.send.SendData;
import com.weaver.inte.jna.send.SendLib;
import com.weaver.inte.jna.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;


public class ApplicationSendMain {

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
                SendData sendData = new SendData(sourceData.p_ndi_name, "Memo", true, true);
                try (SendLib sendLib = new SendLib(sendData)) {
//                    do {
//                        try {
//                            String data = DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss");
//                            System.err.println("send MetadataFrameData：" + data);
//                            MetadataFrameData p_metadata = new MetadataFrameData(data.length(), System.currentTimeMillis(), data);
//                            sendLib.sendMetadata(p_metadata);
//                            Thread.sleep(1000);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } while (true);
                    final int pixelDepth = 4;
                    VideoFrameV2Data p_video_data = new VideoFrameV2Data();
                    ByteBuffer[] frameBuffers = {
                        ByteBuffer.allocateDirect(p_video_data.xres * p_video_data.yres * pixelDepth),
                        ByteBuffer.allocateDirect(p_video_data.xres * p_video_data.yres * pixelDepth)
                    };
                }
            }
        }
    }
}
