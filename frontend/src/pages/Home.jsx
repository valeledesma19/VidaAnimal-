import { Link } from 'react-router-dom';
import './Home.css';

export default function Home() {
  return (
    <div className="home">
      <header className="home-nav">
        <span className="home-logo">VidaAnimal</span>
        <nav>
          <a href="#nosotros">Sobre nosotros</a>
          <a href="#servicios">Servicios</a>
          <a href="#contacto">Contacto</a>
        </nav>
        <Link to="/login" className="btn-nav">Reservar turno</Link>
      </header>

      <section className="hero">
        <svg className="hero-paw" viewBox="0 0 200 200" aria-hidden="true">
          <circle cx="100" cy="130" r="38" />
          <circle cx="55" cy="75" r="20" />
          <circle cx="100" cy="55" r="22" />
          <circle cx="145" cy="75" r="20" />
        </svg>

        <div className="hero-content">
          <h1>Cuidamos a tu mejor amigo como si fuera el nuestro</h1>
          <p className="hero-subtitle">
            Turnos online, control de vacunas y seguimiento de peso para que acompañar
            la salud de tu mascota sea simple.
          </p>
          <div className="hero-actions">
            <Link to="/login" className="btn-primary">Reservar turno</Link>
            <a href="#nosotros" className="btn-secondary">Conocer la clínica</a>
          </div>
        </div>
      </section>

      <svg className="wave-divider" viewBox="0 0 1440 80" preserveAspectRatio="none" aria-hidden="true">
        <path d="M0,40 C360,90 1080,0 1440,40 L1440,80 L0,80 Z" />
      </svg>

      <section id="nosotros" className="seccion nosotros">
        <h2>Sobre nosotros</h2>
        <p>
          VidaAnimal nació de una idea simple: la atención veterinaria no tiene que
          sentirse apurada ni impersonal. Somos un equipo chico que conoce a cada
          paciente por su nombre, con un consultorio pensado para que tanto vos como
          tu mascota se sientan cómodos desde que entran.
        </p>
        <p>
          Trabajamos con turnos ordenados por franja horaria, historial clínico
          digital y recordatorios de vacunación, para que nunca se te pase una
          fecha importante.
        </p>
      </section>

      <section id="servicios" className="seccion servicios">
        <h2>Servicios</h2>
        <div className="grid-servicios">
          <article className="card-servicio">
            <svg viewBox="0 0 48 48" aria-hidden="true">
              <path d="M24 6c-6 0-10 5-10 11 0 5 3 8 3 13 0 6 3 10 7 10s7-4 7-10c0-5 3-8 3-13 0-6-4-11-10-11z" />
            </svg>
            <h3>Consultas generales</h3>
            <p>Chequeos de rutina, diagnóstico y seguimiento clínico de tu mascota.</p>
          </article>

          <article className="card-servicio">
            <svg viewBox="0 0 48 48" aria-hidden="true">
              <rect x="10" y="20" width="28" height="10" rx="2" />
              <rect x="18" y="14" width="4" height="22" />
              <rect x="26" y="14" width="4" height="22" />
            </svg>
            <h3>Vacunación</h3>
            <p>Calendario de vacunas con alertas automáticas antes de cada vencimiento.</p>
          </article>

          <article className="card-servicio">
            <svg viewBox="0 0 48 48" aria-hidden="true">
              <circle cx="24" cy="24" r="16" />
              <line x1="24" y1="24" x2="24" y2="14" />
              <line x1="24" y1="24" x2="31" y2="24" />
            </svg>
            <h3>Control de peso</h3>
            <p>Seguimiento del peso de tu mascota a lo largo del tiempo, visita a visita.</p>
          </article>

          <article className="card-servicio">
            <svg viewBox="0 0 48 48" aria-hidden="true">
              <rect x="8" y="10" width="32" height="28" rx="3" />
              <line x1="8" y1="18" x2="40" y2="18" />
              <line x1="16" y1="6" x2="16" y2="14" />
              <line x1="32" y1="6" x2="32" y2="14" />
            </svg>
            <h3>Turnos online</h3>
            <p>Reservá, editá o cancelá turnos vos misma, sin llamar por teléfono.</p>
          </article>
        </div>
      </section>

      <section id="contacto" className="seccion contacto">
        <h2>Contactanos</h2>
        <div className="contacto-grid">
          <div>
            <h3>Dirección</h3>
            <p>Villa María, Córdoba</p>
          </div>
          <div>
            <h3>Horarios</h3>
            <p>Lunes a sábado, 9:00 a 18:00</p>
          </div>
          <div>
            <h3>Teléfono</h3>
            <p>0353 15-123-4567</p>
          </div>
          <div>
            <h3>Email</h3>
            <p>contacto@vidaanimal.com</p>
          </div>
        </div>
        <Link to="/login" className="btn-primary">Reservar turno ahora</Link>
      </section>

      <footer className="home-footer">
        <p>© {new Date().getFullYear()} VidaAnimal. Todos los derechos reservados.</p>
      </footer>
    </div>
  );
}