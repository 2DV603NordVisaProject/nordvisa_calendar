import React from 'react';
import Miss from 'react-router/Miss';
import Match from 'react-router/Match';
import Redirect from 'react-router/Redirect';
import './Routes.css';
import LoginPage from '../pages/LoginPage';
import RegisterPage from '../pages/RegisterPage';
import RecoverPasswordPage from '../pages/RecoverPasswordPage';
import ChangePasswordPage from '../pages/ChangePasswordPage';
import MyAccountPage from '../pages/MyAccountPage';
import MembersPage from '../pages/MembersPage';
import EventPage from '../pages/EventPage';
import MyEventsPage from '../pages/MyEventsPage';
import GenerateWidgetPage from '../pages/GenerateWidgetPage';
import PendingRegistrationsPage from '../pages/PendingRegistrationsPage';
import Logout from '../components/Logout';
import MatchWhenLoggedIn from '../routes/MatchWhenLoggedIn';
import MatchWhenAdmin from '../routes/MatchWhenAdmin';
import Client from '../Client';


const Routes = () => (
  <div className="view-container">
    <Miss render={({ location }) => (
      <div className="lightbox" style={{ margin: '80px auto' }}>
        <h2>404</h2>
        <h3>{location.pathname} could not be found!</h3>
      </div>
      )}
    />
    <Match
      exactly
      pattern="/"
      render={() => (
        <Redirect to="/login" />
    )}
    />
    <Match pattern="/login" component={LoginPage} />
    <Match pattern="/logout" component={Logout} />
    <Match pattern="/register" component={RegisterPage} />
    <Match pattern="/recover-password" component={RecoverPasswordPage} />
    <Match
      pattern="/update-password/:id"
      render={({ params }) => (
        <ChangePasswordPage id={params.id} />
        )}
    />
    <Match pattern="/generate-widget" component={GenerateWidgetPage} />
    <MatchWhenLoggedIn pattern="/user/account" component={MyAccountPage} />
    <MatchWhenLoggedIn pattern="/user/event/create" component={EventPage} />
    <MatchWhenLoggedIn exactly pattern="/user/event" component={MyEventsPage} />
    <Match
      pattern="/user/event/edit/:id"
      render={({ params }) => {
        if (Client.isLogedIn()) {
          return (
            <EventPage id={params.id} progress="edit" />
          );
        }
        return (
          <Redirect to="/login" />
        );
      }}
    />

    <Match
      pattern="/user/event/view/:id"
      render={({ params }) => {
        if (Client.isLogedIn()) {
          return (
            <EventPage id={params.id} progress="view" />
          );
        }
        return (
          <Redirect to="/login" />
        );
      }}
    />
    <MatchWhenAdmin pattern="/admin/members" component={MembersPage} />
    <MatchWhenAdmin pattern="/admin/pending-registrations" component={PendingRegistrationsPage} />
  </div>
);

export default Routes;
