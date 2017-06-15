import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';
import MapMarker from './MapMarker';

class EventsMap extends Component {

  static defaultProps = {
    center: { lat: 59.329323, lng: 18.068581 },
    zoom: 11,
  };

  render() {
    return (
      <GoogleMapReact
        defaultCenter={this.props.center}
        defaultZoom={this.props.zoom}
        bootstrapURLKeys={{
          key: 'AIzaSyDIKbno4BEY3C76YjjHOvYfDc1kYEAYd4w',
          language: 'en',
        }}
      >

        {
        this.props.events.map(event => <MapMarker lat={event.location.coordinates.coordinates[1]} lng={event.location.coordinates.coordinates[0]} />)
      }
      </GoogleMapReact>
    );
  }

}


export default EventsMap;
