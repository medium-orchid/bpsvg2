package bpsvg2.math

enum class CSSUnits {
    UNITLESS, PERCENT,
    // Lengths
    // - Font units
    CAP, CH, EM, EX,  IC, LH,
    // - Relative font units
    RCAP, RCH, REM, REX, RIC, RLH,
    // - Default viewport
    VH, VW, VMAX, VMIN, VB, VI,
    // - Small viewport
    SVH, SVW, SVMAX, SVMIN, SVB, SVI,
    // - Large viewport
    LVH, LVW, LVMAX, LVMIN, LVB, LVI,
    // - Dynamic viewport
    DVH, DVW, DVMAX, DVMIN, DVB, DVI,
    // - Container query
    CQW, CQH, CQI, CQB, CQMIN, CQMAX,
    // - Absolute
    PX, CM, MM, Q, IN, PC, PT,
    // Time
    S, MS,
    // Frequency
    HZ, KHZ,
    // Resolution
    DPI, DPCM, DPPX;

    operator fun invoke(value: Double): Dimension {
        return Dimension(value, this)
    }
}