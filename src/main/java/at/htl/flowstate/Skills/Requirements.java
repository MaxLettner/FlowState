package at.htl.flowstate.Skills;

public class Requirements {
    private int MeeleRequirement;
    private int RangedRequirement;
    private int MagicRequirement;

    public Requirements(int meeleRequirement, int rangedRequirement, int magicRequirement) {
        MeeleRequirement = meeleRequirement;
        RangedRequirement = rangedRequirement;
        MagicRequirement = magicRequirement;
    }
    public int getMeeleRequirement() {
        return MeeleRequirement;
    }
    public void setMeeleRequirement(int meeleRequirement) {
        MeeleRequirement = meeleRequirement;
    }
    public int getRangedRequirement() {
        return RangedRequirement;
    }
    public void setRangedRequirement(int rangedRequirement) {
        RangedRequirement = rangedRequirement;
    }
    public int getMagicRequirement() {
        return MagicRequirement;
    }
    public void setMagicRequirement(int magicRequirement) {
        MagicRequirement = magicRequirement;
    }
    public boolean checkRequirements(int meeleLevel, int rangedLevel, int magicLevel) {
        return meeleLevel >= MeeleRequirement && rangedLevel >= RangedRequirement && magicLevel >= MagicRequirement;
    }
}
