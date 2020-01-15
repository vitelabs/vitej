package org.vitej.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.vitej.core.constants.CommonConstants;
import org.vitej.core.protocol.methods.request.IssueTokenParams;

import java.util.regex.Pattern;

public final class BuiltinContractUtils {
    private static final Pattern PATTERN_SBP_NAME = Pattern.compile("^([0-9a-zA-Z_.]+[ ]?)*[0-9a-zA-Z_.]$");

    public static String checkSBPName(String sbpName) {
        if (StringUtils.isEmpty(sbpName) || sbpName.length() > 40
                || !PATTERN_SBP_NAME.matcher(sbpName).matches()) {
            return "invalid sbp name";
        }
        return null;
    }

    private static final Pattern PATTERN_TOKEN_NAME = Pattern.compile("^([a-zA-Z_]+[ ]?)*[a-zA-Z_]$");
    private static final Pattern PATTERN_TOKEN_SYMBOL = Pattern.compile("^[A-Z0-9]+$");

    public static String checkIssueTokenParams(IssueTokenParams params) {
        if (params == null) {
            return "invalid params";
        }
        if (StringUtils.isEmpty(params.getTokenName()) || params.getTokenName().length() > 40
                || !PATTERN_TOKEN_NAME.matcher(params.getTokenName()).matches()) {
            return "invalid token name";
        }
        if (StringUtils.isEmpty(params.getTokenSymbol()) || params.getTokenSymbol().length() > 10
                || !PATTERN_TOKEN_SYMBOL.matcher(params.getTokenSymbol()).matches()
                || params.getTokenSymbol().equals("VITE")
                || params.getTokenSymbol().equals("VCP")
                || params.getTokenSymbol().equals("VX")) {
            return "invalid token symbol";
        }
        if (params.getTotalSupply() == null
                || params.getTotalSupply().compareTo(CommonConstants.TT256M1) > 0
                || (!params.isReIssuable() && params.getTotalSupply().signum() == 0)) {
            return "invalid total supply";
        }
        if (params.getMaxSupply() == null
                || params.getMaxSupply().compareTo(CommonConstants.TT256M1) > 0
                || (params.isReIssuable() && params.getMaxSupply().compareTo(params.getTotalSupply()) < 0)
                || (!params.isReIssuable() && params.getMaxSupply().signum() != 0)) {
            return "invalid max supply";
        }
        if (!params.isReIssuable() && params.isOwnerBurnOnly()) {
            return "invalid owner burn only";
        }
        return null;
    }
}
