package de.salomax.currencies.model

import android.content.Context
import de.salomax.currencies.R

enum class Language(
    val iso: String,
    private val nameNative: String?,
    private val nameLocalized: Int
) {
    SYSTEM("system", null, R.string.system_default),
    EN("en", "English", R.string.language_en),
    ZH_CN("zh_CN", "简体中文", R.string.language_zh_CN);

    companion object {
        private val isoMapping: Map<String, Language> = values().associateBy(Language::iso)
        fun byIso(isoValue: String?): Language? =
            // direct match: e.g. de <-> de or e.g. pt_BR <-> pt_BR
            isoMapping[isoValue]
            // either the resource string has no country, or the given locale has none:
            // use only language without country
                ?: isoMapping.mapKeys { it.key.substringBefore("_") }[isoValue?.substringBefore("_")]
    }

    fun nativeName(context: Context): String = when (this) {
        SYSTEM -> context.getString(R.string.system_default)
        else -> this.nameNative as String
    }

    fun localizedName(context: Context): String =
        this.nameLocalized.let { context.getString(it) }

}
