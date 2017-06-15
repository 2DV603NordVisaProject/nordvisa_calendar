import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './MembersView.css';
import Client from '../Client';
import MembersList from './MembersList';
import ConfirmMessage from './ConfirmMessage';
import ErrorList from './ErrorList';

class MembersView extends Component {
  state = {
    members: [],
    updated: [],
    fieldErrors: [],
    popup: {
      pop: false,
      msg: '',
      onYesClick: this.onYesClick,
    },
  }
  componentWillMount() {
    const uri = '/api/admin/manageableUsers';

    Client.get(uri)
      .then((members) => {
        this.setState({ members });
      });
  }


  onFormSubmit(event) {
    event.preventDefault();

    if (this.state.updated.length > 0) {
      let updated = this.state.updated.map(user => user.email);
      updated = updated.join(', ');
      const popup = {
        pop: true,
        msg: `${this.context.language.MembersView.accMsg} ${updated}`,
      };

      this.setState({ popup });
    }
  }

  onInputChange(event) {
    const updated = this.state.updated;
    const members = this.state.members.filter((member) => {
      if (event.target.name === member.id) {
        updated.push({
          id: event.target.name,
          role: event.target.value,
          email: member.email,
        });

        member.userLevel = event.target.value;
        return member;
      }
      return member;
    });

    this.setState({ members, updated });
  }

  onConfirmClick() {
    let updated = this.state.updated;

    while (updated.length > 0) {
      const user = updated.shift();
      let uri = '';

      if (user.role === 'USER') {
        uri = '/api/admin/make_user';
      }

      if (user.role === 'ADMIN') {
        uri = '/api/admin/make_admin';
      }

      if (user.role === 'SUPER_ADMIN') {
        uri = '/api/super_admin/make_super_admin';
      }

      if (user.role === 'DELETE') {
        uri = '/api/user/unregister';
      }

      Client.post({ id: user.id }, uri)
        .then(() => {
          const uri = '/api/admin/manageableUsers';

          Client.get(uri)
            .then((members) => {
              this.setState({ members });
            });
        });
    }

    updated = [];
    const popup = {
      pop: false,
      msg: '',
    };
    const fieldErrors = [];
    fieldErrors.push(this.context.language.Errors.accessUpdated);
    this.setState({ updated, popup, fieldErrors });
    this.forceUpdate();
  }

  render() {
    const language = this.context.language.MembersView;

    return (
      <div className="members view">
        <h2 className="uppercase">{language.members}</h2>
        <form onSubmit={this.onFormSubmit.bind(this)}>
          <MembersList members={this.state.members} onChange={this.onInputChange.bind(this)} />
          <ErrorList errors={this.state.fieldErrors} />
          <input type="submit" value={language.save} className="btn-primary" />
        </form>
        <ConfirmMessage popup={this.state.popup} onClick={this.onConfirmClick.bind(this)} />
      </div>
    );
  }
}

MembersView.contextTypes = {
  language: PropTypes.object,
};

export default MembersView;
