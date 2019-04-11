package com.glwlc.lulu.framework.Flow.dialog;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Gavin
 * @Date: 2019-02-26 19:51
 */
@Data
@NoArgsConstructor
public class DialogData implements Serializable {

    // TODO
    private ChartData chartData;

    private DictateData dictateData;

    private SegmentData segmentData;
}
