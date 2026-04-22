import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import api from "../services/api";

function Movies() {
  const [movies, setMovies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const { user } = useAuth();
  const navigate = useNavigate();

  const fetchMovies = async () => {
    setLoading(true);
    setError("");

    try {
      const response = await api.get("/movies");
      setMovies(response.data || []);
    } catch (apiError) {
      const message = apiError?.response?.data?.error || apiError?.message || "Cannot load movies";
      setError(message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchMovies();
  }, []);

  return (
    <section className="page-shell">
      <div className="page-head">
        <h2>Movie List</h2>
        <p className="muted">Welcome {user?.username}. Choose a movie and continue to booking.</p>
      </div>

      {loading ? <p className="alert">Loading movies...</p> : null}
      {error ? <p className="alert error">{error}</p> : null}

      {!loading && !error ? (
        <div className="movie-grid">
          {movies.map((movie) => (
            <article key={movie.id} className="movie-card">
              <h3>{movie.title}</h3>
              <p className="meta">Genre: {movie.genre}</p>
              <p className="meta">Duration: {movie.durationMinutes} minutes</p>
              <p className="description">{movie.description || "No description"}</p>

              <button
                type="button"
                className="primary-btn"
                onClick={() => navigate(`/booking?movieId=${movie.id}`)}
              >
                Book Ticket
              </button>
            </article>
          ))}
        </div>
      ) : null}
    </section>
  );
}

export default Movies;
