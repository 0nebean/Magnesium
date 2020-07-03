
let vuexMutations = {
    statusGlobalButtonLoding: async function (state) {
        state.globalButtonLoding = !state.globalButtonLoding
    },
    statusGlobalScreenLoding: async function (state) {
        state.globalScreenLoding = !state.globalScreenLoding
    },
    statusGlobalLoginLoding: async function (state) {
        state.globalLoginLoding = !state.globalLoginLoding
    }
}

export default vuexMutations
