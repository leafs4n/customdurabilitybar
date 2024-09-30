package dev.leafs4n.customdurabilitybar.config;

import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = "customdurabilitybar")
@Config(name = "customdurabilitybar", wrapperName = "DurabilityBarConfig")
public class DurabilityBarConfigModel {

    @SectionHeader("AppearanceSection")
    public DurabilityBarType durabilityBarType = DurabilityBarType.GREEN_LEAVES;
    public enum DurabilityBarType {
        GREEN_LEAVES, COLOURFUL_LEAVES, DEFAULT, HIDE;
    }

    public DurabilityBarOrientation durabilityBarOrientation = DurabilityBarOrientation.HORIZONTAL;
    public enum DurabilityBarOrientation{
        HORIZONTAL, VERTICAL;
    }

    @SectionHeader("PositionSection")
    public int xPosition = 0;
    public int yPosition = 0;
}
