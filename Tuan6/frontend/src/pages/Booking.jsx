import { useEffect, useMemo, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import api from "../services/api";

const PRICE_PER_SEAT = 120;

function Booking() {
  const location = useLocation();
  const initialMovieId = new URLSearchParams(location.search).get("movieId") || "";

  const { user } = useAuth();

  const [movies, setMovies] = useState([]);
  const [myBookings, setMyBookings] = useState([]);
  const [form, setForm] = useState({
    movieId: initialMovieId,
    seatCount: 1
  });
  const [submitting, setSubmitting] = useState(false);
  const [loadingData, setLoadingData] = useState(true);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const totalPrice = useMemo(
    () => Number(form.seatCount || 0) * PRICE_PER_SEAT,
    [form.seatCount]
  );

  const loadData = async () => {
    setLoadingData(true);
    setError("");

    try {
      const [moviesResponse, bookingsResponse] = await Promise.all([
        api.get("/movies"),
        api.get("/bookings")
      ]);

      setMovies(moviesResponse.data || []);
      const allBookings = bookingsResponse.data || [];
      setMyBookings(allBookings.filter((item) => item.userId === user?.userId));
    } catch (apiError) {
      const message =
        apiError?.response?.data?.error ||
        apiError?.response?.data?.message ||
        apiError?.message ||
        "Cannot load booking data";
      setError(message);
    } finally {
      setLoadingData(false);
    }
  };

  useEffect(() => {
    loadData();
  }, [user?.userId]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");
    setSuccess("");

    if (!form.movieId) {
      setError("Please select a movie");
      return;
    }

    if (Number(form.seatCount) < 1) {
      setError("Seat count must be at least 1");
      return;
    }

    setSubmitting(true);

    try {
      const response = await api.post("/bookings", {
        userId: user.userId,
        movieId: Number(form.movieId),
        seatCount: Number(form.seatCount),
        totalPrice
      });

      setSuccess(
        `Booking #${response.data.id} created with status ${response.data.status}. Waiting payment result...`
      );

      await loadData();
    } catch (apiError) {
      const message =
        apiError?.response?.data?.error ||
        apiError?.response?.data?.message ||
        apiError?.message ||
        "Cannot create booking";
      setError(message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <section className="page-shell">
      <div className="page-head">
        <h2>Booking Page</h2>
        <p className="muted">Select seats and confirm booking.</p>
      </div>

      {loadingData ? <p className="alert">Loading booking data...</p> : null}
      {error ? <p className="alert error">{error}</p> : null}
      {success ? <p className="alert success">{success}</p> : null}

      <form onSubmit={handleSubmit} className="booking-form">
        <label htmlFor="movieId">Movie</label>
        <select
          id="movieId"
          value={form.movieId}
          onChange={(event) => setForm((prev) => ({ ...prev, movieId: event.target.value }))}
          required
        >
          <option value="">Select movie</option>
          {movies.map((movie) => (
            <option key={movie.id} value={movie.id}>
              {movie.title} ({movie.genre})
            </option>
          ))}
        </select>

        <label htmlFor="seatCount">Seat Count</label>
        <input
          id="seatCount"
          type="number"
          min={1}
          max={10}
          value={form.seatCount}
          onChange={(event) => setForm((prev) => ({ ...prev, seatCount: event.target.value }))}
          required
        />

        <div className="summary-row">
          <span>Price per seat:</span>
          <strong>{PRICE_PER_SEAT}</strong>
        </div>
        <div className="summary-row">
          <span>Total price:</span>
          <strong>{totalPrice}</strong>
        </div>

        <button type="submit" className="primary-btn" disabled={submitting}>
          {submitting ? "Confirming..." : "Confirm Booking"}
        </button>
      </form>

      <div className="booking-history">
        <h3>Your Bookings</h3>
        <p className="muted">Latest status will update after async payment processing.</p>

        {myBookings.length === 0 ? (
          <p className="muted">No bookings yet. Go to <Link to="/movies">Movie List</Link> to start.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Movie ID</th>
                <th>Seats</th>
                <th>Total</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {myBookings.map((booking) => (
                <tr key={booking.id}>
                  <td>{booking.id}</td>
                  <td>{booking.movieId}</td>
                  <td>{booking.seatCount}</td>
                  <td>{booking.totalPrice}</td>
                  <td>{booking.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </section>
  );
}

export default Booking;
