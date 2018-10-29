package ficus.suitcase.fileio;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01333346 on 2018/10/29.
 * @author DamonFicus
 * 文件读写时候，对于大文件的处理
 * 使用org.apache.commons.io 包的 FileUtils
 * 对文件按行处理，避免常规的将文件一次性读取到
 * 内存中造成性能和资源的浪费
 */
public class FileIoUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileIoUtils.class);
    public static final int FILE_NUMBER_PER_WRITE = 100;
    public static final String CHANNEL_CODE = "";
    public static final String FILE_IS_EMPTY = "fileIsEmpty";

    /**
     * 方法说明：<br>
     *
     * @param localRtnFileName
     * @param localAdjFileName
     * @param fileDate
     * @return String
     */
    private String convertFile(String localDir, String localRtnFileName, String localAdjFileName, String fileDate,
                               String corpNo) throws Exception {
        boolean hasFile = Boolean.FALSE;
        String convertFile = new StringBuffer(CHANNEL_CODE).append(SplitCnst.UNDER_LINE)
                .append(fileDate).append(FileCnst.POINT_RTN_SUFFIX).toString();
        File convertFullFile = new File(localDir + convertFile);
        LineIterator lineRtnIterator = FileUtils.lineIterator(new File(localRtnFileName), EncodingCnst.GBK);
        LineIterator lineAdjIterator = FileUtils.lineIterator(new File(localAdjFileName), EncodingCnst.GBK);
        List<String> localLines = new ArrayList<String>();
        String remoteLine = "";
        try {
            if (convertFullFile.exists()) {
                FileUtils.forceDelete(convertFullFile);
            }
            // 文件需要跳过文件头
            if (lineRtnIterator.hasNext()) {
                lineRtnIterator.nextLine();
            }
            while (lineRtnIterator.hasNext()) {
                hasFile = Boolean.TRUE;
                remoteLine = lineRtnIterator.nextLine();
                localLines.add(convertRtnLine(remoteLine, corpNo));
                if (localLines.size() % (ParamInfoCnst.FILE_NUMBER_PER_WRITE
                        + NumberCnst.NUMBER_ONE) == ParamInfoCnst.FILE_NUMBER_PER_WRITE) {
                    FileUtils.writeLines(convertFullFile, EncodingCnst.UTF_8, localLines, Boolean.TRUE);
                    localLines = new ArrayList<String>();
                }
            }
            // 文件需要跳过文件头
            if (lineAdjIterator.hasNext()) {
                lineAdjIterator.nextLine();
            }

            //每100行写一次
            while (lineAdjIterator.hasNext()) {
                hasFile = Boolean.TRUE;
                remoteLine = lineAdjIterator.nextLine();
                localLines.add(convertAdjLine(remoteLine, corpNo, fileDate));
                if (localLines.size() % (ParamInfoCnst.FILE_NUMBER_PER_WRITE
                        + NumberCnst.NUMBER_ONE) == ParamInfoCnst.FILE_NUMBER_PER_WRITE) {
                    FileUtils.writeLines(convertFullFile, EncodingCnst.UTF_8, localLines, Boolean.TRUE);
                    localLines = new ArrayList<String>();
                }
            }

            if (CollectionUtils.isNotEmpty(localLines)) {
                FileUtils.writeLines(convertFullFile, EncodingCnst.UTF_8, localLines, Boolean.TRUE);
            }
        } catch (Exception e) {
            LOGGER.info("downRtnFile转换文件异常！", e);
            throw e;
        } finally {
            LineIterator.closeQuietly(lineRtnIterator);
            LineIterator.closeQuietly(lineAdjIterator);
        }
        FileUtils.forceDelete(new File(localRtnFileName));
        FileUtils.forceDelete(new File(localAdjFileName));
        if (!hasFile) {
            return FILE_IS_EMPTY;
        }
        return convertFile;
    }

    private String convertRtnLine(String remoteLine, String corpNo) {
        String[] contents = remoteLine.split(SplitCnst.SPLIT_COMMA_SEPARATOR);
        //TODO:根据行分隔符将元素按自己的通用模板进行排列整理
        return "";
    }

    /**
     * 方法说明：<br>
     *
     * @param remoteLine
     * @param corpNo
     * @return String
     */
    private String convertAdjLine(String remoteLine, String corpNo, String fileDate) {
        return "";
    }

}
