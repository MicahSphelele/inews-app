package sphe.inews.domain.models

import sphe.inews.domain.enums.NetworkType

data class NetworkData(val networkType: NetworkType, val isConnected: Boolean)