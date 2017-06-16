import React from 'react';
import { shallow, mount } from 'enzyme';
import EventsMap from '../src/components/EventsMap';

describe('EventsMap', () => {
  const events = [{
    location: {
      coordinates: {
        coordinates: [59, 18],
      },
    },
  },
  {
    location: {
      coordinates: {
        coordinates: [59, 18],
      },
    },
  },
  ];
  describe('without props', () => {
    let wrapper;


    beforeEach(() => {
      wrapper = mount(<EventsMap events={events} />);
    });

    it('should be defined', () => {
      expect(wrapper).toBeDefined();
    });

    it('zoom prop should be set to 11 by defaultProps', () => {
      expect(wrapper.props().zoom).toBe(11);
    });

    it('center.lat prop should be set to defaultProps', () => {
      expect(wrapper.props().center.lat).toBeDefined();
    });

    it('center.lng prop should be set by defaultProp', () => {
      expect(wrapper.props().center.lng).toBeDefined();
    });

    it('should contain MapMarker', () => {
      const MapMarker = wrapper.find('MapMarker').length;
      expect(MapMarker).toBeGreaterThan(0);
    });

    it('should contain GoogleMapReact', () => {
      const GoogleMapReact = wrapper.find('GoogleMapReact').length;
      expect(GoogleMapReact).toBe(1);
    });
  });
  describe('with props', () => {
    let wrapper;
    const zoom = 12;
    const center = {
      lat: 59,
      lng: 18,
    };


    beforeEach(() => {
      wrapper = mount(<EventsMap zoom={zoom} events={events} center={center} />);
    });

    it('zoom prop should be set to 12', () => {
      expect(wrapper.props().zoom).toBe(12);
    });

    it('events prop should have a length of 2', () => {
      expect(wrapper.props().events.length).toBe(2);
    });

    it('center.lat prop should be 59', () => {
      expect(wrapper.props().center.lat).toBe(59);
    });

    it('center.lng prop should be 18', () => {
      expect(wrapper.props().center.lng).toBe(18);
    });
  });
});
