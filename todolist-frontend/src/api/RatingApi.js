import URL from "./URL";

class RatingApi {
    static SCORE_ENDPOINT = '/score';
    static LEADERS_ENDPOINT = '/leaders';


    static async getLeaderBoard(top) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + RatingApi.SCORE_ENDPOINT + RatingApi.LEADERS_ENDPOINT + `?top=${top}`,
            {
                headers: {
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,
                }
            });
        return response;
    }

    static async getUserScore(userId) {
        const response = await fetch(
            URL.SERVER_URL + URL.API_VERSION + RatingApi.SCORE_ENDPOINT + `/${userId}`,
            {
                headers: {
                    "Authorization": `Bearer ${sessionStorage.getItem('token')}`,
                }
            });
        return response;
    }
}

export default RatingApi;