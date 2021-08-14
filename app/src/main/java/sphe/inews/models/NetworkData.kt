package sphe.inews.models

import sphe.inews.enums.NetworkType

data class NetworkData(val networkType: NetworkType, val isConnected: Boolean)