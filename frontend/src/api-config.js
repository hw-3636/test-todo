let backedHost;

const hostname = window && window.location && window.location.hostname;

if (hostname === "localhost") {
  backedHost = "http://localhost:8585";
}

export const API_BASE_URL = `${backedHost}`;
